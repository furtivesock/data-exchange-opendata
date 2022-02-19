import streamlit as st
import pandas as pd
import numpy as np
from plotly.subplots import make_subplots
import plotly.graph_objects as go

df = pd.read_csv('data/merged_data.csv')
df['Period'] = df.agg(lambda x: f"{x['Year']}-{x['Month']}", axis=1)
df['Death-on-infection ratio'] = df['CovidDeath'] / df['CovidCases']
countries = np.sort(df['Country'].unique())

st.title("Data exchange project - Polytech Paris-Saclay, 2022")
st.header("Correlations between unemployment, recent graduates employment and COVID-19 threat")

country_selected = st.selectbox("Select a country to display data below :", countries, int(np.where(countries == "France")[0][0]))
country_df = df[df['Country'] == country_selected]
country_df = country_df.sort_values(by=['Year', 'Month'])
country_df['Contamination growth rate'] = country_df['CovidCases'].pct_change()

fig = make_subplots(specs=[[{"secondary_y": True}]])
fig.add_trace(go.Scatter(x=country_df['Period'], y=country_df['Unemployment rate total'] * 10, name="Unemployment rate total (multiplied by 10)"), secondary_y=False)
fig.add_trace(go.Scatter(x=country_df['Period'], y=country_df['Recent graduates employment rate total (20-34yo)'] * 1.5, name="Recent graduates employment rate total (20-34yo) (multiplied by 1.5)"), secondary_y=False)
fig.add_trace(go.Scatter(x=country_df['Period'], y=country_df['Death-on-infection ratio'], name="Death-on-infection ratio"), secondary_y=True)
fig.update_xaxes(title_text="Period")
fig.update_yaxes(title_text="Death-on-infection ratio", secondary_y=True)

st.plotly_chart(fig)

