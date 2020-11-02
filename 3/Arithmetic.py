from sys import stdin

found = False
b = False 
equation = ""

def leftToRight(nums,n,output,string,i,answer):
	global found
	if found:
		return; 
	if(n<1):
		global b
		global equation 
		if(int(output)==int(answer)):
			b = True
			equation = string
			found = True
		return 
	if(int(output)>int(answer)): #end method if the output is greater than answer 
		return 

	leftToRight(nums,n-1,int(output)*int(nums[i]),string+" * "+str(nums[i]),i+1,answer)
	leftToRight(nums,n-1,int(output)+int(nums[i]),string+" + "+str(nums[i]),i+1,answer)

def normalOperation(nums,string,index,answer,output):
	tester = "19 + 30 + 24 + 22 + 10 + 14 + 4 + 21 + 13 + 8 + 25 + 2 + 26 + 7 +29 + 20 + 19 + 22 + 30 + 5 + 8 + 3 + 11 + 21 + 19 + 17 + 4 + 12 + 9 + 28+ 17 + 16 + 11 + 4 + 13 + 3 + 13 + 2 + 22 + 8 + 24 + 29 + 30 + 4 + 24 +4 + 6 + 30 + 28 + 8 + 22 + 17 + 23 + 19 + 21 + 19 + 6 + 25 + 10 + 2 + 10+ 14 + 10"

	global found
	if found:
		return
	if(index==len(nums)):
		#print(string,output)
		global b
		global equation
		if(eval(string)==int(answer)):
			b = True
			equation = string 
			found = True
		return	

	if(int(output)>int(answer)): #end method if the output is greater than answer 
		len_string = len(string)
		#print(str("count: "+string.count("*")))
		if int(str(string.count("*"))) <= 10:
			#print("") #TEST LINE 
			#print(string) #TEST LINE 
			split_check = string.split("+")
			total = 0
			for i in range(len(split_check)):
				#print("split_check is: "+split_check[i]) #TEST LINE
				total+= eval(split_check[i])
			#print(total) #TEST LINE 
			if int(total)>int(answer):
				#print("stopped") #TEST LINE 
				return 
		else:
			return
			

	normalOperation(nums,string+" + "+str(nums[index]),index+1,answer,int(output)+int(nums[index]))
	normalOperation(nums,string+" * "+str(nums[index]),index+1,answer,int(output)*int(nums[index]))
	

# method which gets the relevant information of the current problem before 
# passing it on to the method which checks it reccursively 
def getProblem(nums, solution):
	#reset the global variables for the new problem 
	global b 
	global equation 
	global found
	b = False 
	found = False 

	nums = nums.strip()
	nums = nums.split(" ")
	solution = solution.split(" ")
	answer = solution[0]
	method = solution[1]	
	if(method == 'L'):
		leftToRight(nums,len(nums)-1,int(nums[0]),str(nums[0]),1,answer)
		if(b):
			print(method+" "+answer+" "+equation)
		else: 
			print(method+" "+answer+" "+"impossible")
	elif(method== "N"):
		normalOperation(nums,str(nums[0]),1,answer,nums[0])
		if(b):
			print(method+" "+answer+" "+equation)
		else:
			print(method+" "+answer+" "+"impossible")
	else: 
		print("method must either be N or L")
		return 

def main():
	lines = stdin.read().splitlines() #read in input 
	if(len(lines)%2 !=0):
		print("Input lines must be equal")
		return

	#for loop to transfer the input 
	for x in range(0,len(lines)-1,2):
		getProblem(lines[x],lines[x+1])


if __name__ == '__main__':
	main();
