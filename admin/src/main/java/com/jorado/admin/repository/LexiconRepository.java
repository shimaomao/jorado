package com.jorado.admin.repository;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LexiconRepository {

    @Select("select count(*) count from lexicon.dict where type=#{type}")
    long count(@Param("type") int type);

    @Select("select count(*) count from lexicon.dict where type=#{type} and word=#{word}")
    long exist(@Param("type") int type, @Param("word") String word);

    @Select("select word from lexicon.dict where type=#{type}")
    List<String> getAll(@Param("type") int type);

    @Insert("insert into lexicon.dict(type,word) values(#{type},#{word})")
    int add(@Param("type") int type, @Param("word") String word);

    @Delete("delete from lexicon.dict where type=#{type} and word=#{word}")
    int delete(@Param("type") int type, @Param("word") String word);

    @Select("select COALESCE(MAX(version),0) version from lexicon.publish where type=#{type}")
    int getLastVersion(@Param("type") int type);

    @Insert("insert into lexicon.publish(type,version) values(#{type},#{version})")
    int publish(@Param("type") int type, @Param("version") int version);

    @Delete("delete from lexicon.dict where type=#{type}")
    int clear(@Param("type") int type);

    @Delete("delete from lexicon.publish where type=#{type}")
    int clearVersion(@Param("type") int type);
}

