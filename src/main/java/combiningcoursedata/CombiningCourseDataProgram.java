package combiningcoursedata;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

import combiningcoursedata.option.MakeOptions;
import combiningcoursedata.zip.Zip;


public class CombiningCourseDataProgram {
	
	private String input;	// save data.zip path
	private String output;	// save result.csv or result.xls path
	private boolean help;	// helpPrint
	private ArrayList<String> fileNames;	// save file's path after unzipping data.zip
	
	
	public void run(String[] args) throws IOException {
		Options options = MakeOptions.createOptions();
		if (parseOptions(options, args)) {
			if (help) {
				MakeOptions.printHelp(options);
				return;
			}
			/*
			/**
			 * 자 어떻게 해야할 지 생각해봅시다.
			 * 일단 Zip class에 unzip method를 사용해서 data.zip파일을 압축해제할거에요
			 * 그럼 그 아래 zip파일들이 나오겠죠?
			 * 또 이 zip파일을 풀어야해요
			 * 쪼르륵 다 풀다보면 어떻게 파일이 생성이 될까요?
			 * 
			 * ******************************** commons-compress 를 사용하기로 했어요 ****************
			 * 
			 * data folder -> 0001.zip ~ 0005.zip
			 * 			   -> 0001 folder -> 통일뭐시기1, 통일뭐시기2
			 *             -> 0002 folder -> 통일뭐시기1, 통일뭐시기2
			 *             -> ...
			 *             -> 0005 folder -> 통일뭐시기1, 통일뭐시기2
			 *             
			 * 그럼 이렇게 생성이 되었으니
			 * C:\\git\\JavaFinalProject\\data\\0001에서 통일뭐시기1, 2.xlsx를 읽어야해요
			 * 
			 * JUST 합치기만 하면 된다니까
			 * 구성좀 해볼까요??
			 * 먼저 result.csv파일이에요 (extra point는 나중에 생각하자구요)
			 * 
			 * result.csv
			 * 		studentID,  제목,  요약문(300자 내외),  핵심어(keyword,쉽표로 구분),  조회날짜,  실제자료조회출처(웹자료링크), 원출처(기관명 등), 제작자(Copyright 소유처), 표/그림 일련번호, 자료유형(표, 그림, ...), 자료에 나온 표나 그림 설명(캡션), 자료가 나온 쪽 번호
			 * 
			 * 		0001,	    제목1, 요약	......
			 * 		0001,	    제목2, 요약 	......
			 * 		0001, 		...	
			 * 		0001, 	   제목12, 요약  .......
			 * 		...
			 * 		0005, 	   제목 ...
			 * 		
			 * 뭐 이런식으로 합치면 될 것같아요
			 * 파일을 읽을 때 주의할 점이 있어요.
			 * 통일뭐시기1에서는 1번째 line이 바로 목록인데
			 * 통일뭐시기2에서는 1번째 line이 주석이여서 요고는 넘거야될것같아요.
			 * 
			 * 그리고 보너스에요
			 * result1.xls, result2.xls파일이에요
			 * 
			 * result1.xls
			 * 		studentID,  제목,  요약문(300자 내외),  핵심어(keyword,쉽표로 구분),  조회날짜,  실제자료조회출처(웹자료링크), 원출처(기관명 등), 제작자(Copyright 소유처)
			 * 			0001, 시작
			 * 			...
			 * 			0005, 끝
			 * result2.xls
			 * 		studentID,  제목,  표/그림 일련번호, 자료유형(표, 그림, ...), 자료에 나온 표나 그림 설명(캡션), 자료가 나온 쪽 번호
			 * 			0001, 시작
			 * 			...
			 * 			0005, 끝
			 * 
			 * 이런식으로 만들먼 될 것같아요
			 * 그럼 0002, 0003학생은 사진이 있어서 파일 크기가 큰데 사진은 어떻게 처리할 지 물어볼거에요
			 * 
			 * 
			 * 그리고 예외적으로 0003.zip안에 읽을 수 없는 파일이 있어요
			 * 이 친구들의 fileName을 따로 받아서 
			 * error.csv파일을 생성 후 그 안에 fileName만 저장하면 되는 문제에요
			 * 요 친구는 exception처리만 해결하면 csv파일 만들어서 line추가하는건 쉬울것같아요.
			 * 
			 * 
			 * 채점기준을 준비해볼까요???
			 * Documents
			 * 		여기서 준비할 것은 별로 없을 것 같아요
			 * 		filed랑 method의 사용 이유만 코드짜기 전에 주석으로 적어주면 됩니당.
			 * 		근데 how to execute program은 뭘까요?
			 * Write code based on the Class diagram
			 * 		이건 문제 없어요
			 * 		순서가 반대기 때문이요^^
			 * Use a build tool
			 * 		build.gradle에 application추가하는거 잊지 말자구요
			 * Program is correctly working
			 * 		당연한 소리죠?
			 * Used external libraries
			 * 		이거 사용 안하고 하는게 더 어려울거에요
			 * 		분명히...
			 * Defined Customized Exception
			 * 		이건 slide확인하면서 하면 쉬워요
			 * 		걍 하는거죠
			 * Customized Generics
			 * 		이것도 걍 하는거에요
			 * Used Thread
			 * 		이건 좀 공부해야할것같아요
			 * 		아직 무슨 소린지 잘 모르겠거든요
			 * 		지금 생각으로는 아마 사용한다면, 
			 * 		0002, 0003파일 크기가 좀 큰데 이거 나눠서 하면 빨라지지 않을까요?
			 * Saved two files
			 * 		이건 위에 참고하세요
			 * 		근데 중요한게 각 zip파일 안에있는 xlsx파일을 구분해야 가능한거잖아요
			 * 		이걸 어떻게 구분할 지 생각해봐요
			 * 		두 파일의 차이점은 line.1이 다르다는거랑 그 내용의 차이겠죠?
			 * 
			 * 
			 * 
			 * 이거 솔직히 못하겠어요
			 * 너무 힘들어요 진짜 20시간은 말도 안되는거랍니다.
			 * 그래도 결국 해내면 결과만 나오는거잖아요
			 * 잘하는 사람이 1시간만에 만들어도 결과가 똑같고 못하는 사람이 100시간만에 만들어도 결과가 똑같은데
			 * 이 과정을 교수님은 모르시니까 100시간만에 만들어도 나를 잘하는 학생이라고 생각할까봐 그게 걱정이에요
			 * 포기 안하고 자바를 끝냈지만 다른 과목 다 조져버린 학생 > 깔끔하게 포기하고 적당히 한 다음 다른 과목 캐리한 학생
			 * 이게 참...
			 * 저는 모르겠어요 화이팅
			 */
			File file = new File(input);
			Zip.unzip(file);
		}
	}
	
	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();
		
		try {
			CommandLine cmd = parser.parse(options, args);
			
			input = cmd.getOptionValue("i");
			output = cmd.getOptionValue("o");
			help = cmd.hasOption("h");
			
		} catch (Exception e) {
			MakeOptions.printHelp(options);
			return false;
		}
		return true;
	}
}
