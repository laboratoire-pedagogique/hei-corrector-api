import { expect } from 'chai';
import {sumIntervals} from './sumOfIntervals.js';

describe('KATA : sumIntervals (pt:7)', () => {
    it('should return the sum of interval lengths (p:1)', () => {
        const intervals = [[1, 5]];
        const result = sumIntervals(intervals);
        expect(result).to.equal(4);
    });

    it('should handle overlapping intervals correctly (p:1)', () => {
        const intervals = [[1, 4], [7, 10], [3, 5]];
        const result = sumIntervals(intervals);
        expect(result).to.equal(7);
    });

    it('should handle multiple intervals correctly (p:1)', () => {
        const intervals = [[1, 10], [2, 5]];
        const result = sumIntervals(intervals);
        expect(result).to.equal(9);
    });

    it('should handle multiple overlapping intervals correctly (p:1)', () => {
        const intervals = [[1, 5], [12, 15], [3, 10]];
        const result = sumIntervals(intervals);
        expect(result).to.equal(12);
    });

    it('should handle multiple overlapping intervals with additional intervals correctly (p:1)', () => {
        const intervals = [[1, 5], [12, 15], [3, 10], [13, 18]];
        const result = sumIntervals(intervals);
        expect(result).to.equal(15);
    });

    it('should handle non-overlapping intervals correctly (p:1)', () => {
        const intervals = [[1, 4], [6, 9], [11, 14]];
        const result = sumIntervals(intervals);
        expect(result).to.equal(9);
    });

    it('should handle empty intervals correctly (p:1)', () => {
        const intervals = [];
        const result = sumIntervals(intervals);
        expect(result).to.equal(0);
    });
});
