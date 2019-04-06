# Objective : Visualizing Clustered Data
# Created by: Chiheb Nakkachi
# Created on: 29/03/2019

library(leaflet)
library(leaflet.extras)
library(htmlwidgets)
library(htmltools)
library(webshot)

#reading clustered data saved by the pproject
pathToClusteredData = "clusters.csv"

clusters = read.csv("clusters.csv",sep = ";",encoding = "UTF-8")

#function to choose cluster color
getColor <- function(clusters) {
    sapply(clusters$cluster, function(cluster) {
        if(cluster == 1) {
            "green"
        } else if(cluster == 2) {
            "orange"
        } else {
            "red"
        } })
}

#icon colors
icons <- awesomeIcons(
icon = 'ios-close',
iconColor = 'black',
library = 'ion',
markerColor = getColor(d)
)

#Map plot
map_leaflet = leaflet(d) %>% addTiles() %>%
    addAwesomeMarkers(~X5, ~X4, icon=icons, label=~as.character(X2))%>%
    addLegend("bottomright",
    colors =c("Green",  "Orange", "Red"),
    labels= c("cluster1", "cluster2","cluster3"),
    title= "Cluster colors",
    opacity = 1)%>%
    addControl("Clusterd Data based on Location", position = "topleft")

#saving Map plot in html
saveWidget(map_leaflet, file="Map.html")

#saving Map in pnj
webshot("Map.html", file = "Map.png", cliprect = "viewport")

