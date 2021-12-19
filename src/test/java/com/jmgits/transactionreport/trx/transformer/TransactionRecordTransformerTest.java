package com.jmgits.transactionreport.trx.transformer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.jmgits.transactionreport.base.utils.JsonUtils;
import com.jmgits.transactionreport.test.TransactionTestUtils;
import org.junit.Test;

/**
 * Testing for {@link TransactionRecordTransformer}
 **/
public class TransactionRecordTransformerTest {

    //
    // this test should use a parametrized runner with many more cases

    private final TransactionRecordTransformer transformer = new TransactionRecordTransformer();

    @Test
    public void testTransformInvalidTransaction_Ko() {

        var transaction = TransactionTestUtils.getValidTransaction();
        transaction.setId(null);

        var response = transformer.transform(JsonUtils.write(transaction));

        assertThat(response.isPresent(), is(false));
    }

    @Test
    public void testTransformValidTransaction_Ok() {

        var transaction = TransactionTestUtils.getValidTransaction();

        var response = transformer.transform(JsonUtils.write(transaction));

        assertThat(response.isPresent(), is(true));
    }

}
