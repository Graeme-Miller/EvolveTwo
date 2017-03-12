import docker, requests, time, random, subprocess


def addAnimals(url, number, diet):

    moveSpeedPercentage = random.randint(20, 100),
    maturityAgePercentage = random.randint(20, 100),
    gestationSpeedPercentage = random.randint(20, 100),
    litterSizePercentage = random.randint(20, 100),
    maxAgePercentage = random.randint(20, 100),
    metabolismPercentage = random.randint(20, 100),
    hungerLimitToEatPercentage = random.randint(20, 100)    
    
    print "Adding {} {} animals to {}. moveSpeedPercentage {} maturityAgePercentage {} gestationSpeedPercentage {} litterSizePercentage {} maxAgePercentage {} metabolismPercentage {} hungerLimitToEatPercentage {}".format(number, diet, url, moveSpeedPercentage, maturityAgePercentage, gestationSpeedPercentage, litterSizePercentage, maxAgePercentage, maxAgePercentage, maxAgePercentage)
    
    for x in range(0, number):
        sex = "FEMALE"
        if(x%2==0):
          sex = "MALE"          
          
        r = requests.put(url+"/animal", data={
            'sex': sex,
            'diet':diet,
            'moveSpeedPercentage':  moveSpeedPercentage,
            'maturityAgePercentage':  maturityAgePercentage,
            'gestationSpeedPercentage':  gestationSpeedPercentage,
            'litterSizePercentage':  litterSizePercentage,
            'maxAgePercentage':  maxAgePercentage,
            'metabolismPercentage':  metabolismPercentage,
            'hungerLimitToEatPercentage': hungerLimitToEatPercentage
        })

client = docker.from_env()
numberToCreate = 5

print "starting containers"
for x in range(0, numberToCreate):
    portToExpose = '9%03d'%x    
    client.containers.run("evotwo", detach=True, ports={8080: portToExpose})

print "adding vegetation"
for x in range(0, numberToCreate):   
    portToExpose = '9%03d'%x        
    url = 'http://localhost:%s'%portToExpose    
    
    print "Checking web service is up: "+url
    continueStatusCheck = True 
    while (continueStatusCheck):
      time.sleep(2)
      try:
        continueStatusCheck = requests.get(url).status_code != 200
      except:
       print "exception checking status, ignoring"        
       
    #Set vegetation    
    vegetationSpawnRate = random.randint(1, 150)
    vegetationMaxAge = random.randint(1, 50)
    vegetationNutrition = random.randint(1, 100)
    print "setting vegetation for url {}. vegetationSpawnRate {} vegetationMaxAge {} vegetationNutrition {}".format(url, vegetationSpawnRate, vegetationMaxAge, vegetationNutrition)
    r = requests.put(url+"/vegetationSpawnRate", data={'vegetationSpawnRate': vegetationSpawnRate})
    r = requests.put(url+"/vegetationMaxAge", data={'vegetationMaxAge': vegetationMaxAge})
    r = requests.put(url+"/vegetationNutrition", data={'vegetationNutrition': vegetationNutrition})

print "waiting for vegetation to grow"
time.sleep(20) #allow vegetation to develop    
print "adding animals"
for x in range(0, numberToCreate):   
    portToExpose = '9%03d'%x        
    url = 'http://localhost:%s'%portToExpose    
        
    #Add herbivores
    addAnimals(url, random.randint(100, 500), "HERBIVORE")    
    # addAnimals(url, random.randint(20, 100), "CARNIVORE")       
    
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
        
        print "{}: total animals={}\therbivores={}\tcarnivores={}\tvegitation={}\tpregnant={}".format(portToExpose,totalAnimals,herbivores,carnivores,vegitation, pregnant)
    print "------------------------CHECK------------------------"
    time.sleep(2)
        
        

# for container in client.containers.list(filters={"ancestor": "evotwo"}):
#     print container;
#     container.stop();
    



