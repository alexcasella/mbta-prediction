package com.mbta.test;

import com.mbta.api.Routes;
import com.mbta.entity.AllRoutesEntity;
import com.mbta.entity.ModeEntity;

public class Test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		AllRoutesEntity allRoutes = Routes.getAllRoutes();
		for (ModeEntity modeFromAllRoute : allRoutes.getMode()) {
			System.out.println(modeFromAllRoute.getMode_name());
		}

	}

}
