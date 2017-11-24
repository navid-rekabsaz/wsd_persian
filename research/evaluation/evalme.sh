#!/bin/bash
type=$1
folderpath=$2
lang=$3

if [ "$1" == "" ]; then
    echo 'give me the type at least!'
    exit;
fi

if [ "$2" == "" ]; then
    echo 'come on! Folder path!'
    exit;
fi

if [ "$3" == "" ]; then
    lang='Persian'
fi

words="coach education execution figure job letter match mission mood paper post pot range rest ring scene side soil strain test"

findpath="$folderpath/$type/*.txt.results"
Files=$(find $findpath -type f -follow -print)

for f in $Files
do
    #echo "Deleting $f"
    rm $f

done

for word in $words; do
    echo "evaluating '$word'"

    file="$folderpath/$type/$word""_$type.txt"
    golden="golden/$lang/$word""_gold.txt"
    perl ScorerTask3.pl $file $golden -t $type

done

#findpath="$folderpath/$type/*.txt.results"
#Files=$(find $findpath -type f -follow -print)

prec_all=0.0
recall_all=0.0
precm_all=0.0
recallm_all=0.0

for word in $words; do
    f="$folderpath/$type/$word""_$type.txt.results"


    OLD_IFS="$IFS"
    IFS=","

    i=0
    while read line
    do
        i=$(($i+1))
        if [ "$i" == "3" ]; then
            array=( $line )

            prec=$(echo "${array[0]}" | cut -c13-19)
            recall=$(echo "${array[1]}" | cut -c11-19)
        else
            if [ "$i" == "5" ]; then
                array=( $line )

                precm=$(echo "${array[0]}" | cut -c13-19)
                recallm=$(echo "${array[1]}" | cut -c11-19)
            fi
        fi
    done < $f
    IFS="$OLD_IFS"

    fmeasure=$(perl -e "print 2*(($prec*$recall)/($prec+$recall))")
    echo "Fmeasure '$word' : $fmeasure"

    prec_all=$(perl -e "print $prec_all+$prec")
    recall_all=$(perl -e "print $recall_all+$recall")
    #precm_all=$(perl -e "print $precm_all+$precm")
    #recallm_all=$(perl -e "print $recallm_all+$recallm")


done

#echo $prec_all
#echo $recall_all
#echo $precm_all
#echo $recall_all

prec_all=$(perl -e "print $prec_all/20")
recall_all=$(perl -e "print $recall_all/20")
fmeasure_all=$(perl -e "print 2*(($prec_all*$recall_all)/($prec_all+$recall_all))")
echo "FMeasure All:"$(perl -e "print $fmeasure_all")
