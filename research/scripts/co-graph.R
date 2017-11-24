read <- read.csv("/Users/navid/workspace/papers/04-emnlp15/evaluation/co-graph/results.csv", header=FALSE)

x=unlist(read[1,2:14], use.names = FALSE)
y=c(unlist(read[3,2:14], use.names = FALSE))
#y=c(unlist(read[2,2:14], use.names = FALSE))
yrange<-range(35:48)
#yrange<-range(10:20)
xrange<-range(x)

colors <- c(2,3,4,5,1);

linetype <- c(2,3,4,5,1);

plotchar <- c(18,19,20,21,26);


plot(y, type="o", col=colors[1], lty=linetype[1], pch=plotchar[1], axes=FALSE, ann=FALSE, ylim=yrange)

axis(1, at=1:13, labels=x, cex.axis=0.75)
axis(2, las=1)
box()

title(xlab="p Threshold")
title(ylab="F-measure")

for (i in 2:4) {
	y=c(unlist(read[i*2+1,2:14], use.names=FALSE));
	#y=c(unlist(read[i*2,2:14], use.names=FALSE));
	lines(y, type="b", lwd=1.5, lty=linetype[i], col=colors[i], pch=plotchar[i]);
}

#abline(a = 15.8, b = 0, col = colors[5], lty = 1)
abline(a = 41.8, b = 0, col = colors[5], lty = 1)

legend("topright", yrange[2], c("Dijkstra","Community-based","Static Pagerank","Personalized Pagerank","standard Baseline"), cex=0.8, col=colors, pch=plotchar, lty=linetype, title="Tree")