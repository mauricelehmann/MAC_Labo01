# MAC_Labo01

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
"The Analyzer we are using is StandardAnalyzer, which creates tokens using the Word Break rules from the Unicode Text Segmentation algorithm specified in Unicode Standard Annex #29; converts tokens to lowercase; and then filters out stopwords. Stopwords are common language words such as articles (a, an, the, etc.) and other tokens that may have less value for searching"
### 4. Does the command line demo use stemming? Explain how you find out the answer.
### 5. Is the search of the command line demo case insensitive? How did you find out the answer?
### 6. Does it matter whether stemming occurs before or after stopword removal? Consider this as a general question
