from collections import defaultdict
import sys
import re

# BusRoutes.py
# Etude 8
# Semester 1 2020
#
# Reads lines from a file containing inter-city routes and
# finds the shortest path between the given source and destination.
# 
# @author: Hugo Baird
# @author: Cedric Stephani

class Graph():
    def __init__(self):
        self.routes = defaultdict(list)
        self.distances = {}
    
    def add_route(self, firstLocation, secondLocation, distance):
        self.routes[secondLocation].append(firstLocation)
        self.routes[firstLocation].append(secondLocation)
        self.distances[(firstLocation, secondLocation)] = distance
        self.distances[(secondLocation, firstLocation)] = distance

def shortest_path(graph, source, destination):
    paths = {source: (None, 0)}
    checked = set()
    shortest_path = []
    current_location = source

    while current_location != destination:
        checked.add(current_location)
        locations = graph.routes[current_location]
        for next_location in locations:
            distance = (graph.distances[(current_location, next_location)]) + (paths[current_location][1])
            if next_location in paths:
                if paths[next_location][1] > distance:
                    paths[next_location] = (current_location, distance)  
            else:
                paths[next_location] = (current_location, distance)           
        next_destinations = {location: paths[location] for location in paths if location not in checked}
        current_location = min(next_destinations, key=lambda k: next_destinations[k][1])

    while current_location is not None:
        shortest_path.append(current_location)
        current_location = paths[current_location][0]
    i = len(shortest_path)-1
    while i >= 0:
        print(shortest_path[i], end ="") 
        print("-" if i != 0 else "", end ="")
        i = i - 1
    print("")
 
      
def getCities(cities, lines):
    try:
        for line in lines: 
          splitLine = line.split(',')
          i=0 
          while i<2: 
            exists = "false"
            if splitLine[i] not in cities: 
                cities.append(splitLine[i].strip())
            i = i + 1
        return cities
    except:
        print("Invalid: route set")
        sys.exit(0)

if __name__ == '__main__':
        if sys.__stdin__.isatty():
            print('Invalid: No input')
            sys.exit(0)
        target = sys.stdin.readline()
        targetSplit = target.split(',')
        source = targetSplit[0].strip().lower()
        destination = targetSplit[1].strip().lower()
        if len(targetSplit) != 2 or source == "" or destination == "":
            print("Invalid: route")
            sys.exit(0)
        lines = sys.stdin.readlines() 
        graph = Graph()  
        cities = [] 
        cities = getCities(cities, lines)
        uniqueEdges = []
        for line in lines:
            line = line.lower().strip()
            splitLine = line.split(',')
            firstLocation = splitLine[0].strip()
            secondLocation = splitLine[1].strip()
            weight = splitLine[2].strip()
            if len(splitLine) != 3 or firstLocation == "" or secondLocation == "" or weight == "" or bool(re.match('^[0-9\.]*$',weight)) == False:
                print("Invalid: route set")
                sys.exit(0)
            if (firstLocation + " " + secondLocation not in uniqueEdges):
                uniqueEdges.append(firstLocation + " " + secondLocation)
                uniqueEdges.append(secondLocation + " " + firstLocation)
                graph.add_route(firstLocation, secondLocation, float(weight))
            else:
                print("Invalid: Non-unique routes")
                sys.exit(0)
        shortest_path(graph, source, destination)

