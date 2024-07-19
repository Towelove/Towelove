package blossom.project.towelove.treehole;

import blossom.project.towelove.treehole.config.ArkConfig;
import blossom.project.towelove.treehole.model.enums.ModelEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ArkConfigTest {

    @Autowired
    private ArkConfig arkConfig;

    @Test
    public void testConfigValues() {
        System.out.println(arkConfig.getApiKey());
        System.out.println(arkConfig.getDoubaoLite4kEndpoint());
        System.err.println(ModelEnum.DOU_BAO_LITE_4K.getEndpointId(arkConfig));
    }
}
