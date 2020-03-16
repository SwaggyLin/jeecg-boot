package org.jeecg.modules.pokemon.util;

import cn.hutool.json.JSONUtil;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlBody;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jeecg.modules.pokemon.entity.PokemonSkill;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author linminfeng
 * @Date 2019-11-27 14:02
 */
public class PokemonSkillsSpider extends PokemonSpider {
    public static final String  BASE_URL ="http://wiki.52poke.com/wiki/%E6%8B%9B%E5%BC%8F%E5%88%97%E8%A1%A8";
    @Override
    public String getHtmlPage(String url) throws Exception {
        HtmlPage page = webClient.getPage(url);
        HtmlBody tbody = (HtmlBody) page.getBody();
        DomNodeList<HtmlElement> lists = tbody.getElementsByTagName("table");
        List<PokemonSkill> pokemonSkillList=new ArrayList<PokemonSkill>();

        for(int i=0;i< lists.size();i++){
            String xml = lists.get(i).asXml();
            if(xml.startsWith("<table class=\"eplist roundy")){
                Document document = Jsoup.parse(xml);
                if (i>=4&&i<=11){
                    String areaStr=lists.get(i).getAttribute("class");
                    String area=areaStr.substring(areaStr.indexOf("bg-")+3,areaStr.lastIndexOf("bd-")-1);
                    List<Element> elements=document.getElementsByTag("tr");
                    for (int j=1;j<elements.size();j++){
                        PokemonSkill pokemonSkill=new PokemonSkill();
                        List<Element> tdElements=elements.get(j).getElementsByTag("td");
                        if (tdElements.size()==9){
                            String skillNo=tdElements.get(0).html();
                            String skillName=tdElements.get(1).getElementsByTag("a").get(0).html();
                            if (tdElements.get(1).getElementsByTag("a").size()==2){
                                skillName=tdElements.get(1).getElementsByTag("a").get(1).html();
                            }
                            String japanese=tdElements.get(2).html();
                            String english=tdElements.get(3).html();
                            String skillAttr=tdElements.get(4).getElementsByTag("a").get(0).html();
                            String skillType=tdElements.get(5).getElementsByTag("a").get(0).html();
                            String power=tdElements.get(6).html();
                            String hitRate=tdElements.get(7).html();
                            String SkillPp=tdElements.get(8).html();
                            pokemonSkill.setArea(area);
                            pokemonSkill.setSkillNo(skillNo);
                            pokemonSkill.setSkillName(skillName);
                            pokemonSkill.setJapaneseName(japanese);
                            pokemonSkill.setEnglishName(english);
                            pokemonSkill.setSkillAttr(skillAttr);
                            pokemonSkill.setSkillType(skillType);
                            pokemonSkill.setPower(power);
                            pokemonSkill.setHitRate(hitRate);
                            pokemonSkill.setSkillPp(SkillPp);
                            System.out.println("地区:"+area+"        序号:"+skillNo+"        名称:"+skillName+"        日文名:"+japanese+"        英文名:"+english+"        属性:"+skillAttr+"        类型:"+skillType+"        威力:"+power+"        命中:"+hitRate+"        pp:"+SkillPp);
                            pokemonSkillList.add(pokemonSkill);
                        }
                    }
                }
            }
        }
        return JSONUtil.toJsonStr(pokemonSkillList);
    }

    public static void main(String[] args) throws Exception {
        PokemonSpider pokemonSpider=new PokemonSkillsSpider();
        pokemonSpider.webClientInit();
        String fetchResult=pokemonSpider.getHtmlPage(BASE_URL);
        System.out.println(fetchResult);
        pokemonSpider.closeWebClient();
    }
}
