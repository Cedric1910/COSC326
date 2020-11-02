from sys import stdin

found = False
b = False 
equation = ""

def normalOperation(nums,string,index,answer,output):
	global found
	if found:
		return
	if(index==len(nums)):
		global b
		global equation
		if(eval(string)==int(answer)):
			b = True
			equation = string 
			found = True
		return

	if(int(output)>int(answer)): #end method if the output is greater than answer 
		return 

	normalOperation(nums,string+" * "+str(nums[index]),index+1,answer,int(output)*int(nums[index]))
	normalOperation(nums,string+" + "+str(nums[index]),index+1,answer,int(output)+int(nums[index]))


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
	if(method== "N"):
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