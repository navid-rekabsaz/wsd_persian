import xml.etree.ElementTree as ET
from os import walk
import mysql.connector
import sys

cnx = mysql.connector.connect(host= "127.0.0.1",
                       user="root",
                       database="farsidb")
cursor=cnx.cursor()

add_question = ("INSERT INTO questions(question,word_id) "
                "VALUES (%s, %s)")


for (dirpath, dirnames, filenames) in walk('/Users/navid/workspace/semeval/clwsd2013/TestSent_Task3/'):
    for filename in filenames:
        wordname=filename.replace('.data','')
        
        query = ("select * from words "
                 "WHERE word = %s")
        cursor.execute(query, (wordname, ))

        wordid=cursor.fetchone()[0]
    
        tree = ET.parse(dirpath+filename)
        root = tree.getroot()
        for child in root[0]:
            ques=ET.tostring(child[0]).strip('\t').strip('\n').replace('</context>','').replace('<context>','')
            print ques
            data_question = (ques, wordid)
            try:
                cursor.execute(add_question, data_question)
                cnx.commit()
            except RuntimeError,e:
                print '***ERROR*** ', e
                cnx.rollback()

    break

cursor.close()
cnx.close()

#tree = ET.parse('country_data.xml')
#root = tree.getroot()