from sys import stdin 
import networkx as nx
import matplotlib.pyplot as plt

# Method which checks each node in the G.nodes list is part of the shortest path
# if the node is not in shortest path then it gets added to the other_nodes list
def checkNode(other_nodes,short_nodes,target):
	for node in short_nodes:
		if str(node) == str(target):
			#print(node,target)
			#print("return occured for:",target)
			return other_nodes
	other_nodes.append(target)
	return other_nodes

# Method which checks each edge in the G.edges list is part of the shortest path
# if the edge is not in shortest path then it gets added to the other_edges list
def checkEdge(other_edges,short_edges,target):
	for edge in short_edges:
		if edge == target:
			return other_edges
	other_edges.append(target)
	return other_edges

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
					return
				for l in line:
					l = l.strip().lower()
				line[0] = line[0].strip().lower()
				line[1] = line[1].strip().lower()
				weight = float(line[2])
				if line[0] == "" or line[0] == "" or weight=="":
					print("Error: Invalid route set")
				for edge in G.edges:
					if line[0] == edge[0] and line[1] == edge[1]:
						print("Error: Non-unique route set")
						return
					elif line[0] == edge[1] and line[1] == edge[0]:
						print("Error: Non-unique route set")
						return
				G.add_edge(line[0],line[1],weight=weight)

		#print(G.nodes)
		#print(G.edges)
		pos = nx.fruchterman_reingold_layout(G) #set the algortihm layout for nodes
		shortest_path_nodes = nx.dijkstra_path(G,start_city,end_city) #get the shortest path
		#print("shortest path is:",shortest_path_nodes) 

		#add other nodes to a seperate array to make them blue
		other_nodes =[]
		for node in G.nodes:
			other_nodes = checkNode(other_nodes,shortest_path_nodes,node)

		shortest_path_edges = [] 
		for i in range(len(shortest_path_nodes)-1):
			#print(shortest_path_nodes[i],shortest_path_nodes[i+1])
			for edge in G.edges:
				if edge[0] == shortest_path_nodes[i] and edge[1] == shortest_path_nodes[i+1]:
					shortest_path_edges.append(edge)
				elif edge[1] == shortest_path_nodes[i] and edge[0] == shortest_path_nodes[i+1]:
					shortest_path_edges.append(edge)

		other_edges =[]
		for edge in G.edges:
			other_edges = checkEdge(other_edges,shortest_path_edges,edge)

		#print(shortest_path_nodes)
		nx.draw_networkx_nodes(G,pos,nodelist=shortest_path_nodes,node_color='orange',node_size=500,alpha=0.8)
		nx.draw_networkx_nodes(G,pos,nodelist=other_nodes,node_color='blue',node_size=500,alpha=0.5)

		nx.draw_networkx_edges(G,pos,edgelist=shortest_path_edges,width=2,edge_color='green',style='dashed') #draw edges
		nx.draw_networkx_edges(G,pos,edgelist=other_edges,width=2,alpha=0.5,edge_color='blue',style='dashed') #draw edges

		#nx.draw_networkx_edge_labels(G,pos,edge_labels={('dunedin','palmerston'):'23'})
		edge_labels=dict([((u,v,),d['weight'])
             for u,v,d in G.edges(data=True)])
		nx.draw_networkx_edge_labels(G,pos,edge_labels=edge_labels)

		nx.draw_networkx_labels(G, pos, font_size=10,font_family='sans-serif')#network labels

		#plotting the graph 
		plt.title("Cheapest bus route- COSC326")
		plt.axis('off')
		#plt.show()
		plt.savefig("output.png", format="PNG")

	except ValueError: 
		print("Error: value error has occured")
		return 

	except nx.NetworkXNoPath:
		print("Error: no path to end found.")
		return




if __name__ == '__main__':
    main()