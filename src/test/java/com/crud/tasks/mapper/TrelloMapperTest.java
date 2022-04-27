package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

class TrelloMapperTest {

    private TrelloMapper trelloMapper= new TrelloMapper();

    @Test
    void mapToBoards() {
        //Given
        TrelloListDto trelloListDto1= new TrelloListDto("2","list1", true);
        TrelloListDto trelloListDto2= new TrelloListDto("3","list2", false);
        List<TrelloListDto> trelloListDto= new ArrayList<>();
        trelloListDto.add(trelloListDto1);
        trelloListDto.add(trelloListDto2);
        TrelloBoardDto trelloBoardDto= new TrelloBoardDto("34", "name", trelloListDto);
        List<TrelloBoardDto> trelloBoardDtoList= new ArrayList<>();
        trelloBoardDtoList.add(trelloBoardDto);

        //When
        List<TrelloBoard> trelloBoardListToCheck;
        trelloBoardListToCheck=trelloMapper.mapToBoards(trelloBoardDtoList);

        //Then
        assertEquals(1,trelloBoardListToCheck.size());
        assertEquals("34", trelloBoardListToCheck.get(0).getId());
        assertEquals("name", trelloBoardListToCheck.get(0).getName());
    }

    @Test
    void mapToBoardsDto() {
        //Given
        TrelloList trelloList1= new TrelloList("jkl","list1trello", true);
        TrelloList trelloList2= new TrelloList("8","list2trello", false);
        List<TrelloList> trelloList= new ArrayList<>();
        trelloList.add(trelloList1);
        trelloList.add(trelloList2);
        TrelloBoard trelloBoard= new TrelloBoard("iko", "name8", trelloList);
        List<TrelloBoard> trelloBoardList= new ArrayList<>();
        trelloBoardList.add(trelloBoard);

        //When
        List<TrelloBoardDto> trelloBoardListDtoToCheck;
        trelloBoardListDtoToCheck=trelloMapper.mapToBoardsDto(trelloBoardList);

        //Then
        assertEquals(1,trelloBoardListDtoToCheck.size());
        assertEquals("iko", trelloBoardListDtoToCheck.get(0).getId());
        assertEquals("name8", trelloBoardListDtoToCheck.get(0).getName());
    }

    @Test
    void mapToList() {
        //Given
        TrelloListDto trelloListDto1= new TrelloListDto("2","list1", true);
        TrelloListDto trelloListDto2= new TrelloListDto("3","list2", false);
        List<TrelloListDto> trelloListDto= new ArrayList<>();
        trelloListDto.add(trelloListDto1);
        trelloListDto.add(trelloListDto2);

        //When
        List<TrelloList> trelloListsToCheck;
        trelloListsToCheck= trelloMapper.mapToList(trelloListDto);

        //Then
        assertEquals(2,trelloListsToCheck.size());
        assertEquals("2", trelloListsToCheck.get(0).getId());
        assertEquals("list1", trelloListsToCheck.get(0).getName());
        assertEquals("3", trelloListsToCheck.get(1).getId());
        assertEquals("list2", trelloListsToCheck.get(1).getName());

    }

    @Test
    void mapToListDto() {

        //Given
        TrelloList trelloList1= new TrelloList("2","list1", true);
        TrelloList trelloList2= new TrelloList("3","list2", false);
        List<TrelloList> trelloList= new ArrayList<>();
        trelloList.add(trelloList1);
        trelloList.add(trelloList2);

        //When
        List<TrelloListDto> trelloListDtoToCheck;
        trelloListDtoToCheck= trelloMapper.mapToListDto(trelloList);

        //Then
        assertEquals(2,trelloListDtoToCheck.size());
        assertEquals("2", trelloListDtoToCheck.get(0).getId());
        assertEquals("list1", trelloListDtoToCheck.get(0).getName());
        assertTrue(trelloListDtoToCheck.get(0).isClosed());
        assertEquals("3", trelloListDtoToCheck.get(1).getId());
        assertEquals("list2", trelloListDtoToCheck.get(1).getName());
        assertFalse(trelloListDtoToCheck.get(1).isClosed());
    }

    @Test
    void mapToCardDto() {

        //Given
        TrelloCard trelloCard= new TrelloCard("shopping", "aldi",
                "3", "234");

        //When
        TrelloCardDto trelloCardDto= trelloMapper.mapToCardDto(trelloCard);

        //Then
        assertEquals("shopping", trelloCardDto.getName());
        assertEquals("aldi", trelloCardDto.getDescription());
        assertEquals("3", trelloCardDto.getPos());
        assertEquals("234", trelloCardDto.getListId());
    }

    @Test
    void mapToCard() {

        //Given
        TrelloCardDto trelloCardDto= new TrelloCardDto("shopping", "lidl",
                "5", "23");

        //When
        TrelloCard trelloCard= trelloMapper.mapToCard(trelloCardDto);

        //Then
        assertEquals("shopping", trelloCard.getName());
        assertEquals("lidl", trelloCard.getDescription());
        assertEquals("5", trelloCard.getPos());
        assertEquals("23", trelloCard.getListId());
    }
}