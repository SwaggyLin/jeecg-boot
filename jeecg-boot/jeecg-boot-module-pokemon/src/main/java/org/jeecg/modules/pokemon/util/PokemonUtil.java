package org.jeecg.modules.pokemon.util;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.pokemon.entity.PokemonHandbook;

import java.util.List;
@Slf4j
public class PokemonUtil {
    public static final String UPLOAD_PATH="/opt/mk-vn/upload";
    public static final String FETCH_URL="https://cn.portal-pokemon.com/play/pokedex/api/v1";
    public static final String IMG_URL="https://cn.portal-pokemon.com/play/resources/pokedex";

    /**
     * 抓取宝可梦图鉴数据
     * @return
     */
    public static List<PokemonHandbook> fetchPokemonHandBook(){
        log.info("开始抓取宝可梦图鉴");
        String fetchResult= HttpUtil.get(FETCH_URL);
        log.info("抓取结果："+fetchResult);
        JSONObject jsonObject= JSON.parseObject(fetchResult);
        //将json转为实体List
        List<PokemonHandbook> pokemonList= JSON.parseArray(JSON.toJSONString(jsonObject.get("pokemons")),PokemonHandbook.class);
        return  pokemonList;
    }

    /**
     * 批量保存宝可梦图鉴图片
     * @param pokemonHandbookList
     */
    public static void batchSavePokemonImg(List<PokemonHandbook> pokemonHandbookList){
        for (int i=0;i<pokemonHandbookList.size();i++){
            String ctxPath =UPLOAD_PATH;
            log.info("保存路径"+ctxPath);
            String imgUrl=IMG_URL+pokemonHandbookList.get(i).getFileName();
            log.info("下载路径"+imgUrl);
            String fileName=imgUrl.substring(imgUrl.lastIndexOf("/")+1);
            log.info("文件名"+fileName);
            try {
                HttpUtil.downloadFile(imgUrl,ctxPath+"/img/pm/"+fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
