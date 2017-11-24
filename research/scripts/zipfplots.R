read1 <- as.numeric(scan("/Users/navid/workspace/papers/04-emnlp15/data/word_frequency/fahamshahri3-freq.txt", what="", sep="\n"))
read2 <- as.numeric(scan("/Users/navid/workspace/papers/04-emnlp15/data/word_frequency/ANCwritten-freq.txt", what="", sep="\n"))


## add extra space to right margin of plot within frame
par(mar=c(5, 4, 4, 6) + 0.1)

## Plot first set of data and draw its axis
opt <- options("scipen" = 20)
getOption("scipen")
plot(read1,log="xy", axes=FALSE, xlab="", ylab="", type="n",col="black")
lines(read1, lty=1) 

axis(2, ylim=c(0,max(read1)),as=1)  ## las=1 makes horizontal labels
mtext("Frequency (Hamshahri)",side=2,line=2.5)

axis(1, xlim=c(0,max(length(read1))))
mtext("Rank (Hamshahri)",side=1,line=2.5) 

box()

## Allow a second plot on the same graph
par(new=TRUE)

## Plot the second plot and put axis scale on right
plot(read2, log="xy", xlab="", ylab="", axes=FALSE, type="n", col="grey")
lines(read2, lty=2, col="grey") 

axis(4, ylim=c(0,max(read2)),las=1)
mtext("Frequency (ANC)",side=4,line=2.5, col="black") 

axis(3, xlim=c(0,max(length(read2))))
mtext("Rank (ANC)",side=3,line=2.5, col="black") 

legend("topright",legend=c("Hamshahri","ANC"),
text.col=c("black","grey"),lty=c(1,2),col=c("black","grey"))
