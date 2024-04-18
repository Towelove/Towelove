
package blossom.project.towelove.framework.log;

import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 张锦标
 * @date: 2024/4/18 5:32 PM
 * LoveLogContext
 */

@EqualsAndHashCode
public class LoveLogContext {
    private String title;
    private Map<String, String> tag;

    public void addTag(String tagKey, String tagVal) {
        if (this.tag == null) {
            this.tag = new HashMap();
        }

        this.tag.put(tagKey, tagVal);
    }

    public String getTagVal(String tagKey) {
        return this.tag == null ? null : (String)this.tag.get(tagKey);
    }

    public LoveLogContext() {
    }

    public String getTitle() {
        return this.title;
    }

    public Map<String, String> getTag() {
        return this.tag;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTag(Map<String, String> tag) {
        this.tag = tag;
    }
}
