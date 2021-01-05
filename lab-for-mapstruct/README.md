# mapstruct

MapStruct 是用于生成类型安全的 Bean 映射类的 Java 注解处理器。

与动态映射框架相比，MapStruct 具有以下优点：

    使用纯 Java 方法代替 Java 反射机制快速执行。

    编译时类型安全：只能映射彼此的对象和属性，不能映射一个 Order 实体到一个 Customer DTO 中等等。

    如果无法映射实体或属性，则在编译时清除错误报告。

notice：一定要在 maven-compiler-plugin 插件中，声明 mapstruct-processor 为 JSR 269 的 Java 注解处理器。（见pom.xml）

1.编写一个Convert接口，标注@Mapper，声明为一个MapStruct Mapper映射类

2.调用Mappers的getMapper方法，获得 MapStruct 帮我们自动生成的实现类的对象。

3.定义convert方法

4.对象转换时，存在属性不是完全映射的情况下，比如属性名不同，可以使用@Mapper注解，配置响应的映射关系
```
@Mappings({
        @Mapping(source = "id", target = "userId")
})
```
5 支持多个对象转换为一个对象
```$xslt
@Mappings(value = {
    @Mapping(soruce="adminBO.id",target="id"),
    @Mapping(soruce="adminBO.name",target="name"),
    @Mapping(soruce="accessTokenBO.id",target="token.accessToken")
})
AdminsOAuth2AuthenticateResponce convert(AdminBO adminBO, OAuth2AccessTokenBO accessTokenBO)
```
6 IDEA MapStruct插件 
 
 https://mapstruct.org/news/2017-09-19-announcing-mapstruct-idea/