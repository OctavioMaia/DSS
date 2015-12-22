import random
from unicodedata import normalize
rapaz = []
rapariga = []
aplidos = []
totO = 90000 #Total de Eleitores a gerar 
totReal = float(9682823)
ifdC = {"Aveiro ": 1, "Beja": 2, "Braga": 3, "Braganca": 4, "Castelo Branco": 5, "Coimbra ": 6, "Evora": 7, "Faro": 8, "Guarda": 9, 
"Leiria": 10, "Lisboa": 11, "Portalegre": 12, "Porto": 13, "Santarem": 14, "Setubal": 15, "Viana do Castelo": 16, "Vila Real": 17, 
"Viseu": 18, "Acores": 19, "Madeira": 20, "Europa": 21, "Fora da Europa": 22} 

EleitC = {"Aveiro ": (653541/totReal), "Beja": (128971/totReal), "Braga": (787706/totReal), "Braganca": (147485/totReal), 
"Castelo Branco": (181459/totReal), "Coimbra ": (391029/totReal), "Evora": (141443/totReal), "Faro": (370882/totReal), 
"Guarda": (163508/totReal), "Leiria": (423865/totReal), "Lisboa": (1901335/totReal), "Portalegre": (101246/totReal), 
"Porto": (1591762/totReal),  "Santarem": (393387/totReal), "Setubal": (725783/totReal), "Viana do Castelo": (253271/totReal), 
"Vila Real": (228399/totReal), "Viseu": (371991/totReal), "Acores": (227486/totReal), "Madeira": (255748/totReal), "Europa": (78253/totReal), "Fora da Europa": (164273/totReal)}


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
for key in ifdC:
	i = ifdC[key]
	p = EleitC[key]
	phome = (random.uniform(3.5,6.5)/10.0)
	homem = int(round(p*phome*totO))
	mulher = int(round(p*(1-phome)*totO))
	bis = []
	dic = geraNomes(rapaz,i,bis,homem)
	names = dic["nomes"]
	bis = dic["bis"] 
	for name in names:
		#print name
		ca.write(name+"\n")
	dic = geraNomes(rapariga,i,bis,mulher)
	names = dic["nomes"]
	bis = dic["bis"] 
	for name in names:
		#print name
		ca.write(name+"\n")