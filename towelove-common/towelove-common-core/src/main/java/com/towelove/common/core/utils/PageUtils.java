package com.towelove.common.core.utils;

import com.github.pagehelper.PageHelper;
import com.towelove.common.core.utils.sql.SqlUtil;
import com.towelove.common.core.web.page.PageDomain;
import com.towelove.common.core.web.page.TableSupport;

/**
 * 分页工具类
 * 
 * @author: 张锦标
 * @date: 2023/2/23 18:36
 * Description:
 */
public class PageUtils extends PageHelper
{
    /**
     * 设置请求分页数据
     */
    public static void startPage()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        //检查排序字段 防止SQL注入问题
        String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
        //判断是否需要进行分页参数合理化
        Boolean reasonable = pageDomain.getReasonable();
        PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
    }

    /**
     * 清理分页的线程变量
     * 当我们不需要分页查询的时候
     * 可以使用这个清除下一次的分页查询拦截
     */
    public static void clearPage()
    {
        PageHelper.clearPage();
    }
}
