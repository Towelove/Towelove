/**
 * @author: ZhangBlossom
 * @date: 2024/1/17 19:25
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description:
 */
import blossom.project.towelove.common.utils.StringUtils;
import jodd.util.StringUtil;
import org.apache.commons.collections.set.PredicatedSet;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Result {
    public ArrayList<String> folderIds;
    public ArrayList<String> fileIds;

    public ArrayList<String> getFolderIds() {
        return folderIds;
    }

    public ArrayList<String> getFileIds() {
        return fileIds;
    }

    public Result() {
        this.folderIds = new ArrayList<>();
        this.fileIds = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Result{" +
                "folderIds=" + folderIds +
                ", fileIds=" + fileIds +
                '}';
    }
}

public class Test {
    class Folder {
        public String id;
        public String folderId;

        public Folder(String id, String folderId) {
            this.id = id;
            this.folderId = folderId;
        }
    }

    class File {
        public String id;
        public String folderId;

        public File(String id, String folderId) {
            this.id = id;
            this.folderId = folderId;
        }
    }

    public List<Folder> folders = Stream.of(
            new Folder("folder1", null),
            new Folder("folder2", "folder1"),
            new Folder("folder3", "folder2"),
            new Folder("folder4", null),
            new Folder("folder5", "folder4")
    ).collect(Collectors.toList());
    public List<File> files = Stream.of(
            new File("file1", "folder1"),
            new File("file2", "folder1"),
            new File("file3", "folder1"),
            new File("file4", "folder2"),
            new File("file5", "folder5")
    ).collect(Collectors.toList());

    public List<String> listFileIdsByFolderIds(Set<String> folderIds) {
        return files.stream().filter(f -> folderIds.contains(f.folderId))
                .map(f -> f.id).collect(Collectors.toList());
    }

    public Set<String> listFolderIdsByFolderIds(Set<String> folderIds) {
        Set<String> resultFolder = new HashSet<>();

        for (String folderId : folderIds) {
            List<String> childFolders = folders.stream()
                    .filter(f -> Objects.equals(f.folderId, folderId))
                    .map(f -> f.id)
                    .collect(Collectors.toList());

            resultFolder.addAll(childFolders);

            Set<String> grandchildFolders = listFolderIdsByFolderIds(new HashSet<>(childFolders));
            resultFolder.addAll(grandchildFolders);
        }

        return resultFolder;
    }

    public Result getChildrenIds(Set<String> folderIds) {
        Result result = new Result();

        Set<String> files = new HashSet<>(listFileIdsByFolderIds(folderIds));
        result.fileIds = new ArrayList<>(files);

        Set<String> folders = listFolderIdsByFolderIds(folderIds);
        result.folderIds = new ArrayList<>(folders);

        return result;
    }

    public static void main(String[] args) {
        Test t = new Test();
        Set<String> folderIds = Collections.singleton("folder1");
        Result result = t.getChildrenIds(folderIds);
        System.out.println(result);
    }


}

