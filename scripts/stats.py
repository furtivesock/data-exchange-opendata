# =================
#
# Small script to generate statistics about the generated data.
#
# Author: Tom Mansion
#
# =================

import csv
import os

MERGED_DATA_PATH = "../data/merged_data.csv"

with open(MERGED_DATA_PATH, "r", encoding="ISO-8859-2") as csv_file:
    data = csv.reader(csv_file, delimiter=",")

    # Skip header
    next(data)

    countries = {}
    for row in data:
        print(row)
        try:
            countries[row[2]] += 1
        except KeyError:
            countries[row[2]] = 1

for country in countries:
    print(country, countries[country])

print("\nTotal:", len(countries.values()))

print("\nFor France:", countries["France"],
      "months (", int(countries["France"] / 12), "years)", "\n")
