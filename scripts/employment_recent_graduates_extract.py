# =================
#
# Script used to extract intermediate data from Eurostat 'Employment rates of recent graduates'
#
# Author: Sophie Nguyen
#
# =================

import csv
from tqdm import tqdm
import datetime

from cv2 import COLOR_BAYER_RG2RGB_EA
import country_converter as coco

DATA_FILENAME = "../data/employment_recent_graduates/data.eurostat/tps00053.tsv"
OUTPUT_FILENAME = "../data/employment_recent_graduates/intermediate_generated_data.csv"
START_YEAR = 2009
END_YEAR = 2020
YEARS_COUNT = END_YEAR - START_YEAR + 1

def get_value(initial_value):
    """Get the percentage directly
    Some values can contain statistical symbols so we ignore them
    See https://ec.europa.eu/eurostat/statistics-explained/index.php?title=Tutorial:Symbols_and_abbreviations

    Args:
        initial_value (string): Raw value

    Returns:
        double: Percentage
    """
    value = initial_value
    if ':' in value:
        return None
    elif 'b' in value:
        value = value.split(" ")[0]
    return value

country_codes = []
country_rows = []
output_rows = []

with open(f"data/{DATA_FILENAME}", newline="") as file:
    content = csv.reader(file, delimiter="\t")
    rows = list(content)
    for row in rows[1:]:
        first_column_values = row[0].split(",")
        country_code = first_column_values[5]
        ignore_codes = ["EU27_2020", "EU28", "EA19", "EL"]
        if country_code in ignore_codes:
            continue
        country_codes.append(country_code)

    country_codes = set(country_codes)

    with open(f"output/{OUTPUT_FILENAME}", 'w', newline='') as file:
        fieldnames = ['Year', 'Month', 'Country', 'Recent_graduates_20-34_employment_rate_t', 'Recent_graduates_20-34_employment_rate_m', 'Recent_graduates_20-34_employment_rate_f']
        writer = csv.DictWriter(file, fieldnames=fieldnames)
        writer.writeheader()

        for country in tqdm(country_codes):
            data = { "T": [], "M": [],  "F": [] }
            current_country_rows = [] # of size 3
            for row in rows[1:]:
                row_country, row_sex = row[0].split(",")[5], row[0].split(",")[3]
                if row_country == country:
                    if row_sex == "M":
                        data["M"] = row[1:]
                    elif row_sex == "F":
                        data["F"] = row[1:]
                    elif row_sex == "T":
                        data["T"] = row[1:]

            country_code = "GB" if country == "UK" else country

            years_range = tqdm(range(YEARS_COUNT), position=1, leave=False)
            for i in years_range:
                for j in range(1, 13):
                    output_row = ({
                        "Year": START_YEAR + i,
                        "Month": j,
                        "Country": coco.convert(names=country_code, to='ISO3', not_found=None),
                        "Recent_graduates_20-34_employment_rate_t": get_value(data["T"][i]),
                        "Recent_graduates_20-34_employment_rate_m": get_value(data["M"][i]),
                        "Recent_graduates_20-34_employment_rate_f": get_value(data["F"][i])
                    })
                    writer.writerow(output_row)