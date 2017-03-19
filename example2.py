import docker, requests, time, random, subprocess, datetime


client = docker.from_env()
numberToCreate = 5

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

addAnimals("http://localhost:8080", "herb-1", random.randint(50, 100), "HERBIVORE")
addAnimals("http://localhost:8080", "herb-2", random.randint(50, 100), "HERBIVORE")
addAnimals("http://localhost:8080", "herb-3", random.randint(50, 100), "HERBIVORE")

addAnimals("http://localhost:8080", "carn-3", random.randint(50, 100), "CARNIVORE")



