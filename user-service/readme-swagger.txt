swagger2 是一个api文档框架,采用RESTful风格
集成步骤：
1、添加依赖：
		<!--swagger-->
		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger2</artifactId>
			<version>2.8.0</version>
		</dependency>
		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger-ui</artifactId>
			<version>2.8.0</version>
		</dependency>

2、配置swagger2文件：
a、创建一个Swagger2配置类，添加注解@Configuration 和 @EnableSwagger2 启动swagger功能。
@Configuration
@EnableSwagger2
public class Swagger2 {

}

b、实现createRestApi 创建 Docket @Bean实例方法。
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.kevin.microservicebase.userservice.controller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

注意1、api接口说明信息.apiInfo(apiInfo())
注意2、controller接口的包路径 .apis(RequestHandlerSelectors.basePackage("com.kevin.microservicebase.userservice.controller"))

3、controller 接口添加api生成，并添加说明
    @ApiOperation(value = "更新组织", notes = "根据组织id更新组织")
    @ApiImplicitParams({@ApiImplicitParam(name = "orgId", value = "待更新的组织id", required = true) })
    @RequestMapping(value = "/update/{orgId}", method = RequestMethod.PUT)
    public void update(@PathVariable String orgId, @RequestBody OrgVo orgVo){.......}

4、查看api文档，并调试调用,
启动服务，访问地址：http://localhost:8080/user-service/swagger-ui.html#




































