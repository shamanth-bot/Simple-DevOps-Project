package com.sample.ng;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class ReportListeners  extends TestNGBase implements ITestListener{
	protected ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();

	@Override
	public void onTestStart(ITestResult result) {

		
		Object[] method =result.getParameters();
		String name = ((TestName)method[0]).getTestName();
		  ExtentTest  test = ReportingManager.instance().getExtent().createTest(name);
	        ReportingManager.instance().setExtentTest(test);
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		 ReportingManager.instance().getExtenttest().pass(MarkupHelper.createLabel("PASSED", ExtentColor.GREEN));
	}

	@Override
	public void onTestFailure(ITestResult result) {
		ReportingManager.instance().getExtenttest().fail(result.getThrowable());
		// TODO Auto-generated method stub
	
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		ReportingManager.instance().getExtenttest().skip(MarkupHelper.createLabel("SKIPPED", ExtentColor.ORANGE));

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		ReportingManager.instance().getExtenttest().skip(MarkupHelper.createLabel("FAILED", ExtentColor.RED));
	
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		//reports.get().flush();
		
	}

}
