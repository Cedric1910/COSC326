from sys import stdin 
import networkx as nx
import matplotlib.pyplot as plt

def main():
	first_line = True
	G = nx.Graph()
	try:
		lines = stdin.read().splitlines()
		for line in lines:
			if first_line:
				line = line.split(",")
				if len(line) !=2:
					print("Error: line 1 must consist of format: city, city")
					return 
				start_city = line[0].strip().lower()
				end_city = line[1].strip().lower()
				first_line = False
			else:
				line = line.split(",")
				if len(line) != 3:
					print("Error: input must consist of format: city, city, distance")
				for l in line:
					l = l.strip().lower()
				line[0] = line[0].strip().lower()
				line[1] = line[1].strip().lower()
				G.add_edge(line[0],line[1],weight=float(line[2]))

		pos = nx.fruchterman_reingold_layout(G) #set the algortihm layout for nodes

		#print(G.nodes)
		shortest_path = nx.shortest_path(G, start_city, end_city) #get the shortest path 
		nx.draw_networkx_nodes(G,pos,node_size=500,node_color='blue',alpha=0.5) #draw nodes 

		#for path in shortest_path:
			#correct_path = [(source, destination) for (source, destination, route_distance) in 
							#G.edges(data=True) if str(source) ==  str(path)]
			#print(correct_path)

		nx.draw_networkx_edges(G,pos,width=2,alpha=0.5,edge_color='blue',style='dashed') #draw edges
		nx.draw_networkx_labels(G, pos, font_size=10,font_family='sans-serif')
		#print(nx.shortest_path(G,start_city, end_city))

		plt.title("Cheapest bus route")
		plt.axis('off')
		plt.show()

	except ValueError:
		print("Error: distance must be a number")
		return

if __name__ == '__main__':
    main()


