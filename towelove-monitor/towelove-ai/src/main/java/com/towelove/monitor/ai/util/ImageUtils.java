package com.towelove.monitor.ai.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.awt.image.*;

import ai.djl.*;
import ai.djl.inference.*;
import ai.djl.modality.*;
import ai.djl.modality.cv.*;
import ai.djl.modality.cv.util.*;
import ai.djl.modality.cv.transform.*;
import ai.djl.modality.cv.translator.*;
import ai.djl.repository.zoo.*;
import ai.djl.translate.*;
import ai.djl.training.util.*;
import org.springframework.stereotype.Component;

/**
 * @author: 张锦标
 * @date: 2023/5/10 10:26
 * ImageUtils类
 */
@Component
public class ImageUtils {
    public static String garbageSorting(InputStream inputStream) throws IOException,
            ModelNotFoundException, MalformedModelException {
        //DownloadUtils.download("https://djl-ai.s3.amazonaws.com/mlrepo/model/cv/image_classification/ai/djl/pytorch/resnet/0.0.1/traced_resnet18.pt.gz",
        // "build/pytorch_models/resnet18/resnet18.pt", new ProgressBar());
        //DownloadUtils.download("https://djl-ai.s3.amazonaws.com/mlrepo/model/cv/image_classification/ai/djl/pytorch/synset.txt",
        //        "build/pytorch_models/resnet18/synset.txt", new ProgressBar());

        Translator<Image, Classifications> translator =
                ImageClassificationTranslator.builder().addTransform(new Resize(256)).addTransform(new CenterCrop(224
                        , 224)).addTransform(new ToTensor()).addTransform(new Normalize(new float[]{0.485f, 0.456f,
                        0.406f}, new float[]{0.229f, 0.224f, 0.225f})).optApplySoftmax(true).build();


        Criteria<Image, Classifications> criteria =
                Criteria.builder().setTypes(Image.class, Classifications.class).optModelPath
                                (Paths.get("build/pytorch_models/resnet18")).optOption("mapLocation", "true") // this model requires
                        // mapLocation for GPU
                        .optTranslator(translator).optProgress(new ProgressBar()).build();

        ZooModel model = criteria.loadModel();

        //String path = "D:\\desktop\\photos\\ylg2.jpg";
        Classifications output = null;
        try {
            //File file = new File(path);
            //InputStream inputStream = new FileInputStream(file);
            Image img = ImageFactory.getInstance().fromInputStream(inputStream);
            img.getWrappedImage();
            Predictor<Image, Classifications> predictor = model.newPredictor();
            output = predictor.predict(img);

            System.out.println(output);

            System.out.println("推测为：" + output.best().getClassName()
                    + ", 概率：" + output.best().getProbability());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "推测为：" + output.best().getClassName()
                + ", 概率：" + output.best().getProbability();
    }
}
