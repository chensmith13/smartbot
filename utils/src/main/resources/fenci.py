#-*- encoding:utf-8 -*-
from __future__ import print_function
import io
import sys
try:
    reload(sys)
    sys.setdefaultencoding('utf-8')
except:
    pass

import codecs
from textrank4zh import TextRank4Keyword, TextRank4Sentence
import io
import sys
sys.stderr = io.TextIOWrapper(sys.stderr.buffer, encoding='utf-8')
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')
text = sys.argv[1]
tr4w = TextRank4Keyword()

tr4w.analyze(text=text, lower=True, window=2)  # py2中text必须是utf8编码的str或者unicode对象，py3中必须是utf8编码的bytes或者str对象

print( '关键词：' )
for item in tr4w.get_keywords(20, word_min_len=1):
    print(item.word)

print()
print( '关键短语：' )
for phrase in tr4w.get_keyphrases(keywords_num=20, min_occur_num= 1):
    print(phrase)

tr4s = TextRank4Sentence()
tr4s.analyze(text=text, lower=True, source = 'all_filters')