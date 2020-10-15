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

## 3
## 1. Find out what is a “term vector” in Lucene vocabulary?
From the Lucene doc (https://lucene.apache.org/core/3_6_0/api/core/org/apache/lucene/document/Field.TermVector.html) <br/>
*A term vector is a list of the document's terms and their number of occurrences in that document.* <br />

## 2. What should be added to the code to have access to the “term vector” in the index?
We can build our field with the class Field, wich use an FieldType object. (We actually use subclasses, eg. NumericDocValuesField)
The FieldType can turn true or false the storage  of the terms vector with the setStoreTermVectors(true || false) method. 

## 3. Compare the size of the index before and after enabling “term vector”, discuss the results
So with Luke, we can see that the index is composed of 3203 documents and 26'602 terms. <br/>
We can also get the number of document with the IndexReader class, by the method indexReader.numDocs() <br />
If we enable term vector in our field, we get the same amount of documents wich is normal because the amount of terms didn't changed, we just added additional informations in the vectors.

## 3.2

### 2. Explain the difference of these five analyzers
#### WhitespaceAnalyzer
The WhitespaceAnalyzer use the WhitespaceTokenizer.It is a tokenizer that *divides text at whitespace. Adjacent sequences of non-Whitespace characters form tokens.*
(https://tool.oschina.net/uploads/apidocs/lucene-3.6.0/org/apache/lucene/analysis/WhitespaceAnalyzer.html)
If we look with Luke, we can see that the WhitespaceAnalyzer give us the same amount of documents , but 38'125 terms (vs 26'602 terms for the standard analyser).

#### EnglishAnalyzer
Same as the StandardAnalyser, but will use the default stop words (by the method getDefaultStopSet()). Or with a given CharArraySet of stop words. <br/>
With Luke, it give us 25'716 terms.

#### ShingleAnalyzerWrapper 1 (using shingle size 1 and 2)
This analyser wrap another analyser. By default, it wrap a StandardAnalyser. It will generates more terms because in addition generating a single terms for a word for example, 
it will generate another term for all the couple of 2 terms. <br/>
Luke give us a total of 126'862 terms.

#### ShingleAnalyzerWrapper (using shingle size 1 and 3, but not 2)
Same as the previous one, but will generate single word terms plus three words terms for a total of 165'716 terms. <br/>

#### StopAnalyzer

