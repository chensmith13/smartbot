import sys
import requests
import urllib.parse
import io
from  lxml import etree
import importlib
import httpx
try:
    importlib.reload(sys)
    sys.setdefaultencoding('utf-8')
except:
    pass
sys.stderr = io.TextIOWrapper(sys.stderr.buffer, encoding='utf-8')
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')
if __name__=='__main__':
    url='https://zhidao.baidu.com/search?lm=0&rn=10&pn=0&fr=search&ie=gbk&dyTabStr=null&word={keyword}'
    word=sys.argv[1]
    url=url.format(keyword=urllib.parse.quote(word.encode('gbk')))
    headers={
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36",
        "Referer": "https://www.baidu.com",
        "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
        "Accept-Encoding": "gzip, deflate",
        "Accept-Language": "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6",
    }
    try:
        response=requests.get(url=url,headers=headers)
        response.raise_for_status()
        response.encoding = response.apparent_encoding
    except Exception as e:
        pass
    select=etree.HTML(response.text)
    list1=select.xpath('//*[@id="wgt-list"]/dl[@class="dl"]/dt/a/@href')
    url=list1[0][4:]
    url='https'+url
    #print(url)
    headers={
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36 Edg/110.0.1587.69",
        "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7",
        "Accept-Encoding": "gzip,deflate",
        "Accept-Language": "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6",
    }

    response = httpx.get(url,headers=headers)
    response.encoding='gbk'
    #print(response.headers)
    #print(response.text)
    tree=etree.HTML(response.text)
    ans=tree.xpath('/html/body/div[2]/header/div[1]/span/@class')
    ansall = ''
    #判断是否是问一问
    if(type(ans)==list and len(ans)>0 and ans[0]=='wyw-logo'):
        ans=tree.xpath('/html/body/div[3]/div/section/article/div[4]/div/div/div[3]/div/div[1]/div[2]/div[1]//div[@class="new-chat-item right"]')
        if(len(ans)>0):#若没有摘要
            ans=tree.xpath('/html/body/div[3]/div/section/article/div[4]/div/div/div[3]/div/div[1]/div[2]/div[1]//div[@class="new-chat-item left"]//text()')
            for a in ans:
                a = a.strip('\n')
                a = a.strip('摘要')
                a = a.strip('展开全部')
                ansall += a
        else:#若有摘要
            ans=tree.xpath('/html/body/div[3]/div/section/article/div[4]/div/div/div[3]/div/div[1]/div[2]/div[1]//text()')
            for a in ans:
                a=a.strip('\n')
                a=a.strip('展开全部')
                ansall+=a
    else:#若是正常百度知道
        ans=tree.xpath('/html/body/div[4]/div/section/article/div[4]/div//div[@class="line content"]//div[@accuse="aContent"]//text()')
        for a in ans:
            a=a.strip('\n')
            a=a.strip('展开全部')
            ansall+=a
    ansall.strip('百度百科')
    print(ansall)