package com.travely.travely;

import com.travely.travely.domain.Region;
import com.travely.travely.domain.Store;
import com.travely.travely.dto.region.RegionResponseDto;
import com.travely.travely.dto.region.SimpleStoreResponseDto;
import com.travely.travely.dto.user.UsersSaveRequestDto;
import com.travely.travely.service.RegionService;
import com.travely.travely.web.RegionController;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.springframework.test.web.servlet.MockMvc;
;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
//git @SpringBootTest
public class RegionControllerTest {

    @Autowired
    private WebApplicationContext context;

    //@Rule
    //public final RestDocumentation restDocumentation = new RestDocumentation("build/generated-snippets");//"build/generated-snippets");

    private MockMvc mockMvc;
    //private RestDocumentationResultHandler document;
   // @Mock
    //private RegionService regionService;

   // @InjectMocks
   // private RegionController regionController;

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("build/generated-snippets");
    @Before
    public void setup() {
        //this.document = document("{class-name}");
       // MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
               // .alwaysDo(MockMvcRestDocumentation.document("{class-name}", Preprocessors.preprocessRequest(Preprocessors.prettyPrint()), Preprocessors.preprocessResponse(Preprocessors.prettyPrint())))
                .build();
    }

    @Test
    public void getStoreCount() throws Exception {
       // Region region = new Region(4L,"홍대");
        //RegionResponseDto regionResponseDto = new RegionResponseDto(region);
       // when(regionService.getAllRegion()).thenReturn(regionResponseDto);
        this.mockMvc
                .perform(get("/api/region")
                .accept(MediaType.APPLICATION_JSON))
                        //.header("Authorization", "Basic dXNlcjpzZWNyZXQ="))
                .andExpect(status().isOk())
               // .andExpect(content().contentType("application/json;charset=UTF-8"))
               // .andExpect(jsonPath("$", hasSize(1)))
               // .andExpect(jsonPath("$[0].id", is(4L)))
               // .andExpect(jsonPath("$[0].regionName", is("홍대")))
                //.andDo(document("headers"))
                .andDo(document("hello"//,
                       // RequestDocumentation.requestParameters(
                         //       RequestDocumentation.parameterWithName("dd").description("d")

                        )


//                        responseFields(
//                                fieldWithPath("email.value").description("The Member's email address"),
//                                fieldWithPath("address.city").description("The Member's address city"),
//                                fieldWithPath("address.street").description("The Member's address street"),
//                                fieldWithPath("address.zipCode").description("The Member's address zipCode")
//                        )
                );
       //document.document(preprocessRequest)
//                .andDo(this.document.snippets(
//                        links(
//                        linkWithRel("next").optional().description("다음페이지"),
//                        linkWithRel("prev").optional().description("이전페이지"),
//                        linkWithRel("self").description("현재페이지")),
//                requestParameters(
//                        parameterWithName("page").description("페이지 번호"),
//                        parameterWithName("size").description("리스트 사이즈")),
//                responseFields(
//                        fieldWithPath("_links").type(JsonFieldType.OBJECT).description("<<resources-doctors-show-all-links,Doctors>> Resources"),
//                        fieldWithPath("_embedded.doctors").type(JsonFieldType.OBJECT).description("<<resources-doctors-show-one, Doctor>> Resource").optional(),
//                        fieldWithPath("page").type(JsonFieldType.OBJECT).description("Information On <<overview-pagination, Pagination>>"))));
    }












//        this.mockMvc.perform(get("/").accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isOk()) 서비스가 만들어내는 원하는 응답
//            .andDo(document("index"));


//        .andDo(document("index", responseFields(
//                fieldWithPath("contact.email")
//                        .type(JsonFieldType.STRING)
//                        .optional() .description("The user's email address"))));


    }


