#!/usr/bin/env Rscript
args <- commandArgs(TRUE)
## Read the input data
a<-read.csv(args[1], sep=",",head=TRUE)


## Set the output file name/type
pdf(file="output.pdf")

## Plot your data
plot(a$tick,a$moveSpeedPercentage,ylab="moveSpeedPercentage",xlab="tick", ylim=c(0,100))
plot(a$tick,a$maturityAgePercentage,ylab="maturityAgePercentage",xlab="tick", ylim=c(0,100))
plot(a$tick,a$gestationSpeedPercentage,ylab="gestationSpeedPercentage",xlab="tick", ylim=c(0,100))
plot(a$tick,a$litterSizePercentage,ylab="litterSizePercentage",xlab="tick", ylim=c(0,100))
plot(a$tick,a$maxAgePercentage,ylab="maxAgePercentage",xlab="tick", ylim=c(0,100))
plot(a$tick,a$metabolismPercentage,ylab="metabolismPercentage",xlab="tick", ylim=c(0,100))
plot(a$tick,a$hungerLimitToEatPercentage,ylab="hungerLimitToEatPercentage",xlab="tick", ylim=c(0,100))

## Close the graphics device (write to the output file)
dev.off()
