import random
from unicodedata import normalize
rapaz = []
rapariga = []
aplidos = []
ma= open("rapaz.txt",'r')
mo= open("menina.txt",'r')
ap = open("aplidos.txt",'r')
for line in ma:
	rapaz.append(line.strip())

for line in mo:
	rapariga.append(line.strip())


for line in ap:
	aplidos.append(line.strip())

def remover_acentos(txt, codif='utf-8'):
    return normalize('NFKD', txt.decode(codif)).encode('ASCII','ignore')

def geraBi():
	bi=""
	#print "aqui"
	bi=bi+str(random.randint(0,2))
	if bi=="0":
		bi=""
	for i in range(0,8):
		if bi=="":
			bi=bi+str(random.randint(1,9))
		else:
			bi=bi+str(random.randint(0,9))
	#print bi
	return bi

def geraPin():
	pin=""
	for i in range(0,4):
		pin=pin+str(random.randint(0,9))

	return pin

def geraAplido(arr):
	fname=-1
	sname=-1
	while fname == sname :
		fname = random.randint(0, len(arr)-1)
		sname = random.randint(0, len(arr)-1)

	return  arr[fname]+ " "+ arr[sname]

def geraNomes(arr,cir,bis,n):
	ret = []
	bism = bis
	i=0
	while i<n:
		i+=1
		fname=-1
		sname=-1
		while fname == sname:
			fname = random.randint(0, len(arr)-1)
			sname = random.randint(0, len(arr)-1)
		tolname = (arr[fname] + " " + arr[sname] + " " + geraAplido(aplidos)).strip()
		#if tolname in ret:
		if False:
			i-=1
		else:
			bi=geraBi()
			while bi in bism:
				bi=geraBi()
			bism.append(bi)
			#print bi+" , "+ + " , "++" , "+str(cir)
			ret.append(bi+","+ str(cir)+ ","+remover_acentos(tolname)+","+ geraPin()+"")
	return {"nomes": ret, "bis": bism}

ca = open("nomes.csv",'w')
ca.write("sep=,\n")
for i in range(1,23):
	bis = []
	dic = geraNomes(rapaz,i,bis,500)
	names = dic["nomes"]
	bis = dic["bis"] 
	for name in names:
		#print name
		ca.write(name+"\n")
	dic = geraNomes(rapariga,i,bis,500)
	names = dic["nomes"]
	bis = dic["bis"] 
	for name in names:
		#print name
		ca.write(name+"\n")