# =================
#
# Script used to make the unemployment intermediate data
#
# This script takes two CSV files and merges them into one that follows
# the intermediary format on the theme of unemployment.
#
# The first file format is from the OCED
# The second file format is from Eurostat
#
# Author: Tom Mansion
#
# =================

import os
import csv

OECD_DIR_PATH = "../data/unemployment/data.oecd"
EUROSTAT_DIR_PATH = "../data/unemployment/data.eurostat/data"

# Columns that will be added in the results based on the two files :
existing_data_names = ["Unemp_total"]

# ========= First, the oecd format
oecd_data = []

# load all csv files
csv_files = [f for f in os.listdir(OECD_DIR_PATH) if f.endswith(".csv")]

for csv_file in csv_files:
    print("Loading", csv_file)
    with open(os.path.join(OECD_DIR_PATH, csv_file), "r", encoding="utf-8") as csv_file:
        csv_reader = csv.reader(csv_file, delimiter=",")

        next(csv_reader)  # skip header

        for row in csv_reader:
            # skip empty rows
            if len(row) == 0:
                continue

            data = {
                # The first column is the country name
                # It is ready in the format we want
                "country": row[0],
            }

            # Date
            date = row[5].split("-")
            data["year"] = date[0]
            data["mounth"] = date[1]

            # Data
            if row[2] == "TOT":
                data["data_name"] = "Unemp_total"
            else:
                data["data_name"] = "Unemp_" + row[2]
                if data["data_name"] not in existing_data_names:
                    existing_data_names.append(data["data_name"])

            data["data_value"] = float(row[6])

            oecd_data.append(data)

# ========= Then, the eurostat format
COUNTRY_CONVERSION_EUROSTAT = {
    'Belgique': 'BLG',
    'Bulgarie': 'BGR',
    'Tchéquie': 'CZE',
    'Danemark': 'DNK',
    "Allemagne (jusqu'en 1990, ancien territoire de la RFA)": 'DEU',
    'Estonie': 'EST',
    'Irlande': 'IRL',
    'Grčce': 'GRC',
    'Espagne': 'ESP',
    'France': 'FRA',
    'Croatie': 'HRV',
    'Italie': 'ITA',
    'Chypre': 'CYP',
    'Lettonie': 'LVA',
    'Lituanie': 'LTU',
    'Luxembourg': 'LUX',
    'Hongrie': 'HUN',
    'Malte': 'MLT',
    'Pays-Bas': 'NLD',
    'Autriche': 'AUT',
    'Pologne': 'POL',
    'Portugal': 'PRT',
    'Roumanie': 'ROU',
    'Slovénie': 'SVN',
    'Slovaquie': 'SVK',
    'Finlande': 'FIN',
    'Sučde': 'SWE',
    'Islande': 'ISL',
    'Norvčge': 'NOR',
    'Suisse': 'CHE',
    'Royaume-Uni': 'GBR',
    'Turquie': 'TUR',
    'États-Unis': 'USA',
    'Japon': 'JPN',
}

DATA_CONVERSION_EUROSTAT = {
    "Chômeurs selon la définition de l'OIT - Total": "Unemp_total",
    "Chômeurs selon la définition de l'OIT - Hommes": "Unemp_men",
    "Chômeurs selon la définition de l'OIT - Femmes": "Unemp_women",
    "Chômeurs selon la définition de l'OIT - Moins de 25 ans - Total": "Unemp_15_24",
    "Chômeurs selon la définition de l'OIT - moins de 25 ans - Hommes": "Unemp_15_24_men",
    "Chômeurs selon la définition de l'OIT - moins de 25 ans - Femmes": "Unemp_15_24_women",
    "Chômeurs selon la définition de l'OIT - Plus de 25 ans - Total": "Unemp_25_74",
    "Chômeurs selon la définition de l'OIT - Plus de 25 ans - Hommes": "Unemp_25_74_men",
    "Chômeurs selon la définition de l'OIT - Plus de 25 ans - Femmes": "Unemp_25_74_women"
}

# load all csv files
csv_files = [f for f in os.listdir(EUROSTAT_DIR_PATH) if f.endswith(".csv")]

eurostat_data = []

for csv_file in csv_files:
    with open(os.path.join(EUROSTAT_DIR_PATH, csv_file), "r", encoding="ISO-8859-2") as csv_file:
        csv_reader = csv.reader(csv_file, delimiter=",")

        next(csv_reader)  # skip header

        for row in csv_reader:
            # Skip empty rows
            if len(row) == 0:
                continue

            # Skip rows without data
            if row[5] == ":" or row[5] == "":
                continue

            # Skip rows without clean data
            if row[3] != "Données désaisonnalisées, données non corrigées des effets de calendrier":
                continue

            # Country
            try:
                data = {
                    "country": COUNTRY_CONVERSION_EUROSTAT[row[1]],
                }
            except KeyError:
                continue

            # Date
            date = row[0].split("M")
            data["year"] = date[0]
            data["mounth"] = date[1]

            # Data
            data["data_name"] = DATA_CONVERSION_EUROSTAT[row[4]]
            if data["data_name"] not in existing_data_names:
                existing_data_names.append(data["data_name"])

            data["data_value"] = float(row[5].replace(",", "."))

            eurostat_data.append(data)


# ========= Finally, merge all the data

GOAL = {}
# Format :
# Year, Mounth, Country, "data"

total_data = oecd_data + eurostat_data

for data in total_data:
    if data["year"] not in GOAL:
        GOAL[data["year"]] = {}

    year = GOAL[data["year"]]

    if data["mounth"] not in year:
        year[data["mounth"]] = {}

    mounth = year[data["mounth"]]

    if data["country"] not in mounth:
        mounth[data["country"]] = {}

    country = mounth[data["country"]]

    country[data["data_name"]] = data["data_value"]

# Write to csv
with open("../data/unemployment/intermediate_generated_data.csv", "w", encoding="utf-8") as csv_file:
    csv_writer = csv.writer(csv_file, delimiter=",")
    csv_writer.writerow(["Year", "Mounth", "Country"] + existing_data_names)

    for year in GOAL:
        for mounth in GOAL[year]:
            for country in GOAL[year][mounth]:
                data = []
                for data_name in existing_data_names:
                    if data_name in GOAL[year][mounth][country]:
                        data.append(GOAL[year][mounth][country][data_name])
                    else:
                        data.append("")

                csv_writer.writerow([year, mounth, country] + data)
print("Done")