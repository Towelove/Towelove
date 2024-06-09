package blossom.project.towelove.community.entity;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

/**
 * @author: ZhangBlossom
 * @date: 2024-06-08 19:42:04
 * @contact: QQ:4602197553
 * @contact: WX:zhangblossom0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "comments", autoResultMap = true)
public class Comments {
    // 评论ID
    @TableId
    private Long id;

    // 用户ID
    private Long userId;

    // 文章ID
    private Long postId;

    // 评论内容
    private String content;

    // 父评论ID，用于支持评论的回复
    private Long parentId;

    // 评论时间
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

    // 点赞数量
    private Integer likesNum;

    // 置顶标志（0代表不置顶，1代表置顶）
    private Integer pinned;

    // 子评论列表
    @TableField(exist = false)
    private List<Comments> replies;
}



/*
{
    "data": {
        "cursor_score": "",
        "items": [
            {
                "id": "665dc2c8000000001500a503",
                "model_type": "note",
                "note_card": {
                    "type": "normal",
                    "desc": "通过网友们分享的数据，我们能够看到，在互联网大厂不同岗位、学历的薪资会有所差异。但总体来看，大厂的员工薪资普遍高于行业平均水平。\n并且，去大厂工作，你获得的不仅仅是高薪，还有好福利、好人脉、高格局、开阔的眼界和光鲜的履历……",
                    "image_list": [
                        {
                            "file_id": "",
                            "height": 1596,
                            "info_list": [
                                {
                                    "url": "http://sns-webpic-qc.xhscdn.com/202406091632/212dcef98cbd4f78d7954e3cfa12c2ee/1040g008313j88lu0g46g5p6h0oviolevttvk2ig!nd_prv_wlteh_webp_3",
                                    "image_scene": "WB_PRV"
                                },
                                {
                                    "image_scene": "WB_DFT",
                                    "url": "http://sns-webpic-qc.xhscdn.com/202406091632/ddf00a5a946a323462aef723f372d714/1040g008313j88lu0g46g5p6h0oviolevttvk2ig!nd_dft_wlteh_webp_3"
                                }
                            ],
                            "stream": {},
                            "live_photo": false,
                            "width": 1235,
                            "url": "",
                            "trace_id": "",
                            "url_pre": "http://sns-webpic-qc.xhscdn.com/202406091632/212dcef98cbd4f78d7954e3cfa12c2ee/1040g008313j88lu0g46g5p6h0oviolevttvk2ig!nd_prv_wlteh_webp_3",
                            "url_default": "http://sns-webpic-qc.xhscdn.com/202406091632/ddf00a5a946a323462aef723f372d714/1040g008313j88lu0g46g5p6h0oviolevttvk2ig!nd_dft_wlteh_webp_3"
                        },
                        {
                            "width": 1236,
                            "url": "",
                            "url_pre": "http://sns-webpic-qc.xhscdn.com/202406091632/f710c66881ef1668e2ed12725ed3159d/1040g008313j88lu0g45g5p6h0oviolev751mis8!nd_prv_wlteh_webp_3",
                            "url_default": "http://sns-webpic-qc.xhscdn.com/202406091632/0a12448d7905c5f0b87fe447933d01c9/1040g008313j88lu0g45g5p6h0oviolev751mis8!nd_dft_wlteh_webp_3",
                            "file_id": "",
                            "height": 1582,
                            "trace_id": "",
                            "info_list": [
                                {
                                    "image_scene": "WB_PRV",
                                    "url": "http://sns-webpic-qc.xhscdn.com/202406091632/f710c66881ef1668e2ed12725ed3159d/1040g008313j88lu0g45g5p6h0oviolev751mis8!nd_prv_wlteh_webp_3"
                                },
                                {
                                    "image_scene": "WB_DFT",
                                    "url": "http://sns-webpic-qc.xhscdn.com/202406091632/0a12448d7905c5f0b87fe447933d01c9/1040g008313j88lu0g45g5p6h0oviolev751mis8!nd_dft_wlteh_webp_3"
                                }
                            ],
                            "stream": {},
                            "live_photo": false
                        },
                        {
                            "trace_id": "",
                            "url_default": "http://sns-webpic-qc.xhscdn.com/202406091632/5ce498bc68d678aaa939bb23b1aa4ba9/1040g008313j88lu0g4505p6h0oviolev7eu6k5o!nd_dft_wlteh_webp_3",
                            "live_photo": false,
                            "url": "",
                            "info_list": [
                                {
                                    "image_scene": "WB_PRV",
                                    "url": "http://sns-webpic-qc.xhscdn.com/202406091632/da2751a42a1f356a6f67e5f3fa472fe9/1040g008313j88lu0g4505p6h0oviolev7eu6k5o!nd_prv_wlteh_webp_3"
                                },
                                {
                                    "image_scene": "WB_DFT",
                                    "url": "http://sns-webpic-qc.xhscdn.com/202406091632/5ce498bc68d678aaa939bb23b1aa4ba9/1040g008313j88lu0g4505p6h0oviolev7eu6k5o!nd_dft_wlteh_webp_3"
                                }
                            ],
                            "url_pre": "http://sns-webpic-qc.xhscdn.com/202406091632/da2751a42a1f356a6f67e5f3fa472fe9/1040g008313j88lu0g4505p6h0oviolev7eu6k5o!nd_prv_wlteh_webp_3",
                            "stream": {},
                            "file_id": "",
                            "height": 1617,
                            "width": 1236
                        },
                        {
                            "file_id": "",
                            "width": 1232,
                            "trace_id": "",
                            "url_pre": "http://sns-webpic-qc.xhscdn.com/202406091632/d4e90ceca9efb48bff52cf04424207b3/1040g008313j88lu0g4405p6h0oviolev278qfdo!nd_prv_wlteh_webp_3",
                            "live_photo": false,
                            "height": 1578,
                            "url": "",
                            "info_list": [
                                {
                                    "image_scene": "WB_PRV",
                                    "url": "http://sns-webpic-qc.xhscdn.com/202406091632/d4e90ceca9efb48bff52cf04424207b3/1040g008313j88lu0g4405p6h0oviolev278qfdo!nd_prv_wlteh_webp_3"
                                },
                                {
                                    "url": "http://sns-webpic-qc.xhscdn.com/202406091632/0bedd6a82aeb54e961d11a3f80088edb/1040g008313j88lu0g4405p6h0oviolev278qfdo!nd_dft_wlteh_webp_3",
                                    "image_scene": "WB_DFT"
                                }
                            ],
                            "url_default": "http://sns-webpic-qc.xhscdn.com/202406091632/0bedd6a82aeb54e961d11a3f80088edb/1040g008313j88lu0g4405p6h0oviolev278qfdo!nd_dft_wlteh_webp_3",
                            "stream": {}
                        },
                        {
                            "file_id": "",
                            "url": "",
                            "info_list": [
                                {
                                    "image_scene": "WB_PRV",
                                    "url": "http://sns-webpic-qc.xhscdn.com/202406091632/77cffd025397c459b55bd7cee21fdefd/1040g008313j88lu0g44g5p6h0oviolevj9p2nn8!nd_prv_wlteh_webp_3"
                                },
                                {
                                    "image_scene": "WB_DFT",
                                    "url": "http://sns-webpic-qc.xhscdn.com/202406091632/c3fc715a1b41c5dc823609f00110a346/1040g008313j88lu0g44g5p6h0oviolevj9p2nn8!nd_dft_wlteh_webp_3"
                                }
                            ],
                            "height": 1591,
                            "width": 1235,
                            "trace_id": "",
                            "url_pre": "http://sns-webpic-qc.xhscdn.com/202406091632/77cffd025397c459b55bd7cee21fdefd/1040g008313j88lu0g44g5p6h0oviolevj9p2nn8!nd_prv_wlteh_webp_3",
                            "url_default": "http://sns-webpic-qc.xhscdn.com/202406091632/c3fc715a1b41c5dc823609f00110a346/1040g008313j88lu0g44g5p6h0oviolevj9p2nn8!nd_dft_wlteh_webp_3",
                            "stream": {},
                            "live_photo": false
                        }
                    ],
                    "tag_list": [],
                    "at_user_list": [],
                    "time": 1717420744000,
                    "last_update_time": 1717420744000,
                    "note_id": "665dc2c8000000001500a503",
                    "user": {
                        "user_id": "64d1063f000000000b0055df",
                        "nickname": "在世间",
                        "avatar": "https://sns-avatar-qc.xhscdn.com/avatar/1040g2jo30qgebursg6605p6h0oviolevv9s84d8"
                    },
                    "interact_info": {
                        "collected_count": "114",
                        "comment_count": "23",
                        "share_count": "81",
                        "followed": false,
                        "relation": "none",
                        "liked": false,
                        "liked_count": "121",
                        "collected": false
                    },
                    "ip_location": "广东",
                    "share_info": {
                        "un_share": false
                    },
                    "title": "2024年大厂校招薪资爆料"
                }
            }
        ],
        "current_time": 1717921923388
    },
    "code": 0,
    "success": true,
    "msg": "成功"
}
 */
