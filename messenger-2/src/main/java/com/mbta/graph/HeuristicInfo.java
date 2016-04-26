/**
 * 
 */
package com.mbta.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * @author andy
 *
 */
public class HeuristicInfo {

	private Vertex stop;

	private double D_start_current;

	private double D_current_end_walk;

	private double D_current_end_train;

	private double D_real;

	private double D_estimate;

	public List<PathInfo> path;

	public HeuristicInfo() {
		this.D_real = Double.POSITIVE_INFINITY;
		this.D_estimate = Double.POSITIVE_INFINITY;
		this.path = new ArrayList<>();
	}

	public Vertex getStop() {
		return stop;
	}

	public void setStop(Vertex stop) {
		this.stop = stop;
	}

	public double getD_start_current() {
		return D_start_current;
	}

	public void setD_start_current(double d_start_current) {
		D_start_current = d_start_current;
	}

	public double getD_current_end_train() {
		return D_current_end_train;
	}

	public void setD_current_end_train(double d_current_end_train) {
		D_current_end_train = d_current_end_train;
	}

	public double getD_real() {
		return D_real;
	}

	public void setD_real(double d_real) {
		D_real = d_real;
	}

	public double getD_estimate() {
		return D_estimate;
	}

	public void setD_estimate(double d_estimate) {
		D_estimate = d_estimate;
	}

	public double getD_current_end_walk() {
		return D_current_end_walk;
	}

	public void setD_current_end_walk(double d_current_end_walk) {
		D_current_end_walk = d_current_end_walk;
	}

}
