# Inverted index with a boolean retrieval model

## Vocabulary and document pre-processing

### Document size

To avoid low precision or recall, we consider each Scene of every Shakespeare play as an independent document. This prevents words that have no connection with each other in the play (say Beatrice and Dogberry in Much Ado About Nothing) from turning up in the same document. Instead, they'll be OR'ed together and shown as multiple separate results. 

### Tokenization

We tokenize on spaces, with the observation that Shakespeare's plays often do not contain multi-word phrases where one word could refer to some other phrase entirely. A hypothetical example of such a conflict would be the presence of both "Beatrice of Messina" and "Beatrice of Paris" such that the two of them are different people.  

### Stop words

`InvertedIndex/StopWords.java` contains the stop list used for this index.

### Normalization

The goal here would be to create mapping rules such that synonyms are also understood by our index (think river and stream), but for now we'll remove special characters and case-fold every word in the index to lowercase. 

### Stemming

To reduce each term to a base word, we use Porter's algorithm. We used [Stanford's NLP repo](https://raw.githubusercontent.com/stanfordnlp/CoreNLP/master/src/edu/stanford/nlp/process/Stemmer.java) as a starting point for our implementation.

## Boolean retrieval model 