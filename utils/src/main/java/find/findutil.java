package find;


import domain.Question;
import org.xm.Similarity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class findutil {
    static String path= findutil .class.getClassLoader().getResource("fenci.py").getPath();
    static String path1=findutil.class.getClassLoader().getResource("pachong.py").getPath();
    static
    {
        path1=path1.substring(1,path1.length());
        path1=path1.replace("/","\\");
        path=path.substring(1,path.length());
        path=path.replace("/","\\");
    }
    public static String findkeywords(String s)
    {
        Process proc;
        String keywords=null;
        String keyword=null;
        try
        {
            String sentence=s;
            String[] args1=new String[]{"python",path,sentence};
            proc=Runtime.getRuntime().exec(args1);
            BufferedReader in=new BufferedReader(new InputStreamReader(proc.getInputStream(),"UTF-8"));
            String line=in.readLine();
            ArrayList<String> arrayList=new ArrayList<>();
            while(line!=null)
            {
                arrayList.add(line);
                line=in.readLine();
            }
            in.close();
            proc.waitFor();
            proc.destroy();
            for(int i=0;i<arrayList.size();++i)
            {
                if("关键短语：".equals(arrayList.get(i))&&i+1<arrayList.size())
                    keywords=arrayList.get(i+1);
                if("关键词：".equals((arrayList.get(i)))&&!("关键短语：").equals(arrayList.get(i+1)))
                    keyword=arrayList.get(i+1);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        if(keyword!=null)
            keyword=keyword.replaceAll("\\s*","");
        if(keywords!=null)
            keywords=keywords.replaceAll("\\s*","");
        if(keywords!=null&&!keywords.isEmpty())
            return keywords;
        else if(keyword!=null&&!keyword.isEmpty())
            return keyword;
        else
            return s;
    }
    public static String findbyzhidao(String s)
    {
        String ans=null;
        Process proc;
        try
        {
            String sentence=s;
            String[] args1=new String[]{"python",path1,sentence};
            proc=Runtime.getRuntime().exec(args1);
            BufferedReader in=new BufferedReader(new InputStreamReader(proc.getInputStream(),"UTF-8"));
            String line=in.readLine();
            if(line!=null)
                ans="";
            while(line!=null)
            {
                ans+=line;
                line=in.readLine();
            }
            in.close();
            int num=proc.waitFor();
            //System.out.println(num);
            proc.destroy();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        //System.out.println(ans);
        return ans;//返回null或者空字符串则为没找到
    }
    public static int findbestmatch(String s, List<Question> arrayList)
    {
        int num=-1;
        double best=-1;
        for(int i=0;i<arrayList.size();++i)
        {
            double morphoSimilarityResult = Similarity.morphoSimilarity(s, arrayList.get(i).getMcontent());
            if(morphoSimilarityResult>best)
            {
                best=morphoSimilarityResult;
                num=i;
            }
        }
        System.out.println(best);
        if(best<=0.7)
            return -1;
        else
            return num;
    }
}
