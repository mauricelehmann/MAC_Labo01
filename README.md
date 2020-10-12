# MAC_Labo01


## 2.1

### 1. What are the types of the fields in the index?
- The path of the file : Stringfield
- The last modified date of the file : Longpoint
- The contents of the file : TextField

### 2. What are the characteristics of each type of field in terms of indexing, storage and tokenization?
- Path of the file :  is searchable, but no tokenization, term frenquency or positional information 
- Last modified date of the file : Indexed
- The contents of the file : Tokenized & indexed
### 3. Does the command line demo use stopword removal? Explain how you find out the answer.
Yes, given to the Apache documentation (https://lucene.apache.org/core/4_1_0/demo/) : <br/>
"The Analyzer we are using is StandardAnalyzer, which creates tokens using the Word Break rules from the Unicode Text Segmentation algorithm specified in Unicode Standard Annex #29; converts tokens to lowercase; and **then filters out stopwords**. Stopwords are common language words such as articles (a, an, the, etc.) and other tokens that may have less value for searching"
### 4. Does the command line demo use stemming? Explain how you find out the answer.
No, the stemming process depend of the language of the text. Given to the same Apache documentation : "It should be noted that there are different rules for every language, and you should use the proper analyzer for each.". <br/>
In the demo, we use the StandardAnalyzer wich fit for a basic usage of indexation.
### 5. Is the search of the command line demo case insensitive? How did you find out the answer?
Yes, it is not case sensitive, as it is said in the same documentation : <br/>
"The query parser is constructed with an analyzer used to interpret your query text in the same way the documents are interpreted: finding word boundaries, **downcasing**, and removing useless words like 'a', 'an' and 'the'."
### 6. Does it matter whether stemming occurs before or after stopword removal? Consider this as a general question
It depend of what you want. Say you have "stemmed" words in your stopwords list, if you remove the stopwords after the stemming, it will remove a lot of words. <br/>
According to Jordan Boyd-Graber (https://www.quora.com/What-should-be-done-first-stopwords-removal-or-stemming-Why-In-weka-should-I-perform-stemming-to-stopwords-list-so-the-word-abl-can-be-removed) : <br/>
"Given the choice, I think it would be better to have unstemmed stopwords and apply it before stemming. This is more of an issue in highly inflected languages, where it's hard to create an unstemmed list of stopwords."


## 2.2

TODO : ??? rien compris...
