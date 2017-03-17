import docker, requests, time, random, subprocess, datetime


def addAnimals(url, speciesId, number, diet):

    moveSpeedPercentage = random.randint(20, 100),
    maturityAgePercentage = random.randint(20, 100),
    gestationSpeedPercentage = random.randint(20, 100),
    litterSizePercentage = random.randint(20, 100),
    maxAgePercentage = random.randint(20, 100),
    metabolismPercentage = random.randint(20, 100),
    hungerLimitToEatPercentage = random.randint(20, 100)
    geneticMutationPercentage = random.randint(20, 100)
    
    #print "Adding {} {} animals to {}. moveSpeedPercentage {} maturityAgePercentage {} gestationSpeedPercentage {} litterSizePercentage {} maxAgePercentage {} metabolismPercentage {} hungerLimitToEatPercentage {}".format(number, diet, url, moveSpeedPercentage, maturityAgePercentage, gestationSpeedPercentage, litterSizePercentage, maxAgePercentage, maxAgePercentage, maxAgePercentage)

    for x in range(0, number):
        sex = "FEMALE"
        if(x%2==0):
          sex = "MALE"          
          
        r = requests.put(url+"/animal", data={
            'sex': sex,
            'diet':diet,
            'speciesId': speciesId,
            'moveSpeedPercentage':  moveSpeedPercentage,
            'maturityAgePercentage':  maturityAgePercentage,
            'gestationSpeedPercentage':  gestationSpeedPercentage,
            'litterSizePercentage':  litterSizePercentage,
            'maxAgePercentage':  maxAgePercentage,
            'metabolismPercentage':  metabolismPercentage,
            'hungerLimitToEatPercentage': hungerLimitToEatPercentage,
            'geneticMutationPercentage': geneticMutationPercentage
        })

def addVegetation(url):
    #print "Checking web service is up: "+url
    continueStatusCheck = True
    while (continueStatusCheck):
      time.sleep(2)
      try:
        continueStatusCheck = requests.get(url).status_code != 200
      except:
        pass
      # print "exception checking status, ignoring"

    #Set vegetation
    vegetationSpawnRate = random.randint(1, 150)
    vegetationMaxAge = random.randint(1, 50)
    vegetationNutrition = random.randint(1, 100)
    #print "setting vegetation for url {}. vegetationSpawnRate {} vegetationMaxAge {} vegetationNutrition {}".format(url, vegetationSpawnRate, vegetationMaxAge, vegetationNutrition)
    r = requests.put(url+"/vegetationSpawnRate", data={'vegetationSpawnRate': vegetationSpawnRate})
    r = requests.put(url+"/vegetationMaxAge", data={'vegetationMaxAge': vegetationMaxAge})
    r = requests.put(url+"/vegetationNutrition", data={'vegetationNutrition': vegetationNutrition})

client = docker.from_env()
numberToCreate = 2

print "starting containers"
containerDictionary = dict()
for x in range(0, numberToCreate):
    portToExpose = '9%03d'%x
    containerDictionary[portToExpose] = datetime.datetime.now().time()
    client.containers.run("evotwo", detach=True, ports={8080: portToExpose})

print "adding vegetation"
for x in range(0, numberToCreate):   
    portToExpose = '9%03d'%x
    url = 'http://localhost:%s'%portToExpose
    addVegetation(url)

print "waiting for vegetation to grow"
time.sleep(20) #allow vegetation to develop    
print "adding animals"
for x in range(0, numberToCreate):   
    portToExpose = '9%03d'%x        
    url = 'http://localhost:%s'%portToExpose    
        
    #Add herbivores
    addAnimals(url, "herb-1", random.randint(50, 100), "HERBIVORE")
    addAnimals(url, "herb-2", random.randint(50, 100), "HERBIVORE")
    addAnimals(url, "herb-3", random.randint(50, 100), "HERBIVORE")

    addAnimals(url, "carn-3", random.randint(50, 100), "CARNIVORE")
    
print "------------------------Started------------------------"    

while (True):
    for x in range(0, numberToCreate):
        portToExpose = '9%03d'%x
        url = 'http://localhost:%s'%portToExpose
                
        totalAnimals = subprocess.check_output("curl -s "+url+" | jq 'map(select(.inhabitant != null))' | jq 'map(select(.inhabitant.identifier != \"Ve\"))' | jq '.[]|.inhabitant.age' | wc -l", shell=True).rstrip()
        herbivores =   subprocess.check_output("curl -s "+url+" | jq 'map(select(.inhabitant.diet == \"HERBIVORE\"))' | jq '.[]|.inhabitant.age' | wc -l", shell=True).rstrip()
        carnivores =   subprocess.check_output("curl -s "+url+" | jq 'map(select(.inhabitant.diet == \"CARNIVORE\"))' | jq '.[]|.inhabitant.age' | wc -l", shell=True).rstrip()
        vegitation =   subprocess.check_output("curl -s "+url+" | jq 'map(select(.inhabitant.identifier == \"Ve\"))' | jq '.[]|.inhabitant.age' | wc -l", shell=True).rstrip()
        pregnant =     subprocess.check_output("curl -s "+url+" | jq 'map(select(.inhabitant != null))' | jq 'map(select(.inhabitant.identifier != \"Ve\"))' | jq 'map(select(.inhabitant.pregnancyCountdown != 0))' | jq '.[]|.inhabitant.age' | wc -l", shell=True).rstrip()

        print "{}: time started={}\ttotal animals={}\therbivores={}\tcarnivores={}\tvegitation={}\tpregnant={}".format(portToExpose, containerDictionary[portToExpose], totalAnimals,herbivores,carnivores,vegitation, pregnant)

        if(int(totalAnimals) == 0):
            command = "docker ps -a | grep \"0.0.0.0:"+portToExpose+"->8080\" |  awk \'{print $1 }\' | xargs -I {} docker rm -f {}"
            subprocess.check_output(command, shell=True)
            containerDictionary[portToExpose] = datetime.datetime.now().time()
            client.containers.run("evotwo", detach=True, ports={8080: portToExpose})

            addVegetation(url)
            addAnimals(url, "herb-1", random.randint(50, 100), "HERBIVORE")
            addAnimals(url, "herb-2", random.randint(50, 100), "HERBIVORE")
            addAnimals(url, "herb-3", random.randint(50, 100), "HERBIVORE")

            addAnimals(url, "carn-3", random.randint(50, 100), "CARNIVORE")

    print "------------------------CHECK------------------------"
    time.sleep(2)
        
        

# for container in client.containers.list(filters={"ancestor": "evotwo"}):
#     print container;
#     container.stop();
    



