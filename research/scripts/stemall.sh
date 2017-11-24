#!/bin/bash

Files=$(find data_processed -type f -follow -print)

for f in $Files
do
    echo "Processing $f"

    text=$(perl perstem.pl -o translit --form untouched < $f)
    echo $text | sed 's/-//g' > "$f.cleansed.translit"


    perl perstem.pl -o utf8 -i translit --form untouched < "$f.cleansed.translit" > "$f.cleansed"

    rm "$f.cleansed.translit"

    perl perstem.pl -o translit --pos --stem < "$f.cleansed" > "$f.pos"
    perl perstem.pl -o utf8 --stem < "$f.cleansed" > "$f.stem"
done
