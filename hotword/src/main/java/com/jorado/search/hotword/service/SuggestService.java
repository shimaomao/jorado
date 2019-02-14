package com.jorado.search.hotword.service;

import com.jorado.search.hotword.model.enums.HotWordType;
import com.jorado.search.hotword.model.suggest.SuggestResult;
import com.jorado.core.Result;

/**
 * Created by len.zhang on 2018/3/30.
 * 热词联想接口
 */
public interface SuggestService {
    Result<SuggestResult> dataSuggest(HotWordType flag, String kw, int rows, boolean debug);
}
