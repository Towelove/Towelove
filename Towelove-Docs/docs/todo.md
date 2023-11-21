---
sidebar: auto
---

# TODO

## Guava包，Hutool包都是必须学会的包（代办）

## 自己编写一个分页类用于处理分页（代办）


这样做的目的是，只需要编写一个分页类，比如PageResult，然后那么我们就可以把结果封装到这个分页类里面，而不是每次再去调用mp的IPage，去复杂的封装这样一个对象。我们可以让一个类继承我们自己编写的分页类，那么当我们以这个类为查询条件的时候，前端可以直接把分页的条件封装再这个类中即可



![img](https://www.notion.so/image/https%3A%2F%2Fcdn.nlark.com%2Fyuque%2F0%2F2023%2Fpng%2F34806522%2F1678094360150-ae655fb4-3704-4e80-ae4f-61d84669d8ff.png?id=5b8945bb-0c07-421f-b716-0af5a47ba948&table=block&spaceId=b64c4d64-f403-4087-86e0-a1e8fdb8361f&width=2000&userId=c25e5a34-6e8a-4a43-ba9f-f420d2f8d2b7&cache=v2)

![img](https://www.notion.so/image/https%3A%2F%2Fcdn.nlark.com%2Fyuque%2F0%2F2023%2Fpng%2F34806522%2F1678094372207-8da97446-3fed-4f6f-8bac-c43496b4b542.png?id=ccbdd22d-b05a-42ac-b2d7-7a57870cf6e0&table=block&spaceId=b64c4d64-f403-4087-86e0-a1e8fdb8361f&width=2000&userId=c25e5a34-6e8a-4a43-ba9f-f420d2f8d2b7&cache=v2)


那么我们要做的就是去封装一个方法，来帮助我们完成我们自己编写的分页类转换为IPage这个分页类，也就是mp的分页类



![img](https://www.notion.so/image/https%3A%2F%2Fcdn.nlark.com%2Fyuque%2F0%2F2023%2Fpng%2F34806522%2F1678085991003-828152b0-7c5b-4bc8-a469-c88c6de1c65b.png?id=812e9c55-a284-41e6-829d-d396346465a9&table=block&spaceId=b64c4d64-f403-4087-86e0-a1e8fdb8361f&width=2000&userId=c25e5a34-6e8a-4a43-ba9f-f420d2f8d2b7&cache=v2)