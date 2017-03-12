import docker, requests, time, random, subprocess


def addAnimals(url, number, diet):
    for x in range(0, number):
        sex = "FEMALE"
        if(x%2==0):
          sex = "MALE"
        r = requests.put(url+"/animal", data={
            'sex': sex,
            'diet':diet,
            'moveSpeedPercentage':random.randint(20, 100),
            'maturityAgePercentage':random.randint(20, 100),
            'gestationSpeedPercentage':random.randint(20, 100),
            'litterSizePercentage':random.randint(20, 100),
            'maxAgePercentage':random.randint(20, 100),
            'metabolismPercentage':random.randint(20, 100),
            'hungerLimitToEatPercentage':random.randint(20, 100)
        })

client = docker.from_env()
numberToCreate = 15

for x in range(0, numberToCreate):
    portToExpose = '9%03d'%x    
    client.containers.run("evotwo", detach=True, ports={8080: portToExpose})
    
for x in range(0, numberToCreate):
    portToExpose = '9%03d'%x        
    url = 'http://localhost:%s'%portToExpose    
            
    #Set vegetation    
    r = requests.put(url+"/vegetationSpawnRate", data={'vegetationSpawnRate': random.randint(1, 50)})
    r = requests.put(url+"/vegetationMaxAge", data={'vegetationMaxAge': random.randint(1, 40)})
    r = requests.put(url+"/vegetationNutrition", data={'vegetationNutrition':random.randint(1, 100)})
    
    #Add herbivores
    addAnimals(url, random.randint(200, 400), "HERBIVORE")    
    addAnimals(url, random.randint(50, 100), "CARNIVORE")       
    
print "------------------------Started------------------------"    

while (True):
    for x in range(0, numberToCreate):
        portToExpose = '9%03d'%x
        url = 'http://localhost:%s'%portToExpose
                
        totalAnimals = subprocess.check_output("curl -s "+url+" | jq 'map(select(.inhabitant != null))' | jq 'map(select(.inhabitant.identifier != \"Ve\"))' | jq '.[]|.inhabitant.age' | wc -l", shell=True).rstrip()
        herbivores = subprocess.check_output("curl -s "+url+" | jq 'map(select(.inhabitant.diet == \"HERBIVORE\"))' | jq '.[]|.inhabitant.age' | wc -l", shell=True).rstrip()
        carnivores = subprocess.check_output("curl -s "+url+" | jq 'map(select(.inhabitant.diet == \"CARNIVORE\"))' | jq '.[]|.inhabitant.age' | wc -l", shell=True).rstrip()
        vegitation = subprocess.check_output("curl -s "+url+" | jq 'map(select(.inhabitant.identifier == \"Ve\"))' | jq '.[]|.inhabitant.age' | wc -l", shell=True).rstrip()
        
        
        print "{}: total animals={}\therbivores={}\tcarnivores={}\tvegitation={}".format(portToExpose,totalAnimals,herbivores,carnivores,vegitation)
    print "------------------------CHECK------------------------"
    time.sleep(5)
        
        

# for container in client.containers.list(filters={"ancestor": "evotwo"}):
#     print container;
#     container.stop();
    



