#read <- read.csv("/Users/navid/workspace/papers/04-emnlp15/evaluation/wordbase/bestresults.csv", header=TRUE)
read <- read.csv("/Users/navid/workspace/papers/04-emnlp15/evaluation/wordbase/oofresults.csv", header=TRUE)


x=names(read[,2:21])
y=c(unlist(read[1,2:21], use.names = FALSE))
yrange<-range(10:100)
#yrange<-range(0:50)

colors <- c(1,1,1,1,1);
plotchar <- c(0,2,3,4);


plot(y, col=colors[1], pch=plotchar[1], axes=FALSE, ann=FALSE, ylim=yrange)

axis(1, at=1:20, labels=FALSE)
#text(x=seq(1,20,by=1),rep(-4,20), label=x, adj=1, srt=60, xpd=TRUE)
text(x=seq(1,20,by=1),rep(1.4,20), label=x, adj=1, srt=60, xpd=TRUE)
axis(2, las=1)
box()

title(xlab="words")
title(ylab="F-measure")

for (i in 2:4) {
	y=c(unlist(read[i,2:21], use.names=FALSE));
	points(y, lwd=1.5, col=colors[i], pch=plotchar[i]);
}

legend("topleft", yrange[2], c("RelAgg","RelGreedy","CO-Graph Dijkstra","Standard Baseline"), cex=0.8, col=colors, pch=plotchar)