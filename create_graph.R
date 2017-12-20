#!/usr/bin/env Rscript
args <- commandArgs(TRUE)
## Read the input data
a<-read.csv(args[1], sep=",",head=TRUE)


## Set the output file name/type
pdf(file="output.pdf")

## Plot your data
plot(a$time,a$moveSpeedPercentage,ylab="moveSpeedPercentage",xlab="tick", ylim=c(0,100))
plot(a$time,a$maturityAgePercentage,ylab="maturityAgePercentage",xlab="tick", ylim=c(0,100))
plot(a$time,a$gestationSpeedPercentage,ylab="gestationSpeedPercentage",xlab="tick", ylim=c(0,100))
plot(a$time,a$litterSizePercentage,ylab="litterSizePercentage",xlab="tick", ylim=c(0,100))
plot(a$time,a$maxAgePercentage,ylab="maxAgePercentage",xlab="tick", ylim=c(0,100))
plot(a$time,a$metabolismPercentage,ylab="metabolismPercentage",xlab="tick", ylim=c(0,100))
plot(a$time,a$hungerLimitToEatPercentage,ylab="hungerLimitToEatPercentage",xlab="tick", ylim=c(0,100))

## Close the graphics device (write to the output file)
dev.off()
