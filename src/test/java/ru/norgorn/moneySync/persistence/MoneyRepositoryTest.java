package ru.norgorn.moneySync.persistence;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.norgorn.moneySync.persistence.entity.Money;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes={TestDataConfig.class})
public class MoneyRepositoryTest {

	@Autowired
	MoneyRepository sut;

	List<Money> dataFromSut;
	
	//@Test
	public void getTopPlayersByCountry_ExpectedPlayersSortedByMoneyForOneCountry(){
		givenDatabaseWithData();
		Collections.reverse(initialData);

		int top = 4;
		whenGetTopPlayersPerCountry(top);

		expectedDataSorted(top);
		
		top = 3;
		whenGetTopPlayersPerCountry(top);

		expectedDataSorted(top);
		
		assertOneCountry();
	}

	private void givenDatabaseWithData() {
		sut.save(initialData);
	}

	private void whenGetTopPlayersPerCountry(int top) {
		dataFromSut = sut.getTopPlayersByCountry("ru",new PageRequest(0, top));
	}

	private void expectedDataSorted(int top) {
		IntStream.range(0, top)
			.forEach(number -> assertTrue("Got wrong players or order",sameElementsInData(number)));
	}
	
	private void assertOneCountry(){
		dataFromSut.forEach(money -> assertSame("Unexpected country", "ru", money.getCountry()));
	}

	private boolean sameElementsInData(int number) {
		String uidFromSut = dataFromSut.get(number).getUid();
		return initialData.get(number).getUid().equals(uidFromSut);
	}

	List<Money> initialData = Arrays.asList(new Money[] {
			new Money("0", "us", 0)
			, new Money("1", "ru", 1)
			, new Money("2", "ru", 2)
			, new Money("3", "ru", 3)
			, new Money("4", "ru", 4)
	}
			); 
}
