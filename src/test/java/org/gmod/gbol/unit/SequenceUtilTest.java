package org.gmod.gbol.unit;

import junit.framework.TestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gmod.gbol.util.GBOLUtilException;
import org.gmod.gbol.util.SequenceUtil;

public class SequenceUtilTest extends TestCase {

    private final Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    public void testTranslateSequence() throws GBOLUtilException {
        logger.info("== testTranslateSequence() ==");
        String nucleotides = "ATGAATCAGTACGGAAGAGAACAGCAAGATACTGGCCTCGTCGGCTCTGGTACAGGACATCGCGATGAATACGGCAATCCCAGGCAAGAGGGTATAATGGACAAGGTGAAAAATGCCGTAGGCATGGGCCCCAGTTCAGGAACCGGCTACAACAATCAGCCTGGTTATGACAATTACGGTAACCCAAGGCAAGAAGGATTAGTAGACAAGGCGAAGGACGCCGTGGGCATGGGTCCGAGTTTAGGAACTGGCTACAATAACCAGCCTGGTTATGACAGTTACGGGAATCGTGAGGGCATTGTGGACAGGGCGAAAGATGCGGTAGGGATGGGTCCGAATTCAGGAACTGGCTACAACAACCAGCCTGGGTACGACAATTACGGTGACCGAAGGCATGAAGGATTGGCAGACAGAGCGAAGGATGCTGTAGGTATGGGGCCTAACTCAGGTTACAACCACCAGCCTGGATATGACAACTACGGCAATCGTGAGGGCGTTGTGGACAAGGCGAAGGATGCGGTAGGCATGGGTCCGAATTCAGGAACTGGCTACAACAACCAGCCTGGTTATGACAGTTATGGTACCCGGAGACAGGAAGGATTGGTAGATAGAGCAAAGGATGCCGTCGGCATGGGCCCCAATTCGGGCACCGGCTATAACAATCAGCCCGGATATGACAACTACGGTAACCCAAGACGCGAAGGAGTGGTAGACAGGGCGAAGGATGCTGTAGGTATGGGGCCTAACTCAGGTTACAACAATCAGCCCGGATATGACAACTACGGCAATCGTGAGGGCATTGTGGACAAAGCCAAGGATGCAGTCGGTGTTGGCCCCCACTCGGGTACTGGCTACCACAACCAGCCCAGCTACGACAACTATGGCAACCCTAGGCAAGAGGGAATCGTGGATAGAGCGAAAGACGCTGTGGGGATGGGACCAAACTCTGGAACTGGCTACAACAACCAGTCTGATTATGACAGTTATGGCAACCCAAGGCACGAAGGCATGCTTGACAAGGCGAAGGATGACTTTGATATGGGCCCCAATTCCGGCACTGGCTATAACAACCGGCCCGGCTATGACACCTATGGGGACCGAAAACACGAGGGAATTGGTGACAAGGTGAGGGACGCAATCGGTACTGGCCCAAACTCCGGATATGACAGCCGCACACCCACCGGAACCGACGCTTACGTGCATGGCAACCATCCCCCTGGTATGCAAGACAGAATCACTGGCGTGAACGAGCCCTCGATCTTAGGTGGACGTGAGAATGTAGACCGCCATGGTTTTGGACACGATGGTCGCCAACATCACGGTCTGCTAGATAATGTTACTCTTCAAAGTGGCCATATTCCTGAGACTATGGTAGGCGGGCGCCGTGTTGAACCTGGATATGATATGACCAAGAGTGCTGGACATCATCTTACTGATCTTGGCCATCACGGTAACGATAGCGGTGTCACTGGATTGGGCCATCACGACACTGATTACGATGAGAGGAGGGGAAAAGGATTTGAAGACCCGATTGATAACAAAACCGGACTTGGATCAGACTACGATACGACCGAGACCGGATCTGGTTATGGTGCCACCGATACTGGTGCTGCACCTCACAAGAAGGGAATCATAACTAAGATCAAGGAGAAGCTGCACCACTAG";
        String translationNoStop = SequenceUtil.translateSequence(nucleotides, SequenceUtil.getTranslationTableForGeneticCode(1));
        logger.info("translation (no stop): " + translationNoStop);
        assertEquals("translation (no stop): ", "MNQYGREQQDTGLVGSGTGHRDEYGNPRQEGIMDKVKNAVGMGPSSGTGYNNQPGYDNYGNPRQEGLVDKAKDAVGMGPSLGTGYNNQPGYDSYGNREGIVDRAKDAVGMGPNSGTGYNNQPGYDNYGDRRHEGLADRAKDAVGMGPNSGYNHQPGYDNYGNREGVVDKAKDAVGMGPNSGTGYNNQPGYDSYGTRRQEGLVDRAKDAVGMGPNSGTGYNNQPGYDNYGNPRREGVVDRAKDAVGMGPNSGYNNQPGYDNYGNREGIVDKAKDAVGVGPHSGTGYHNQPSYDNYGNPRQEGIVDRAKDAVGMGPNSGTGYNNQSDYDSYGNPRHEGMLDKAKDDFDMGPNSGTGYNNRPGYDTYGDRKHEGIGDKVRDAIGTGPNSGYDSRTPTGTDAYVHGNHPPGMQDRITGVNEPSILGGRENVDRHGFGHDGRQHHGLLDNVTLQSGHIPETMVGGRRVEPGYDMTKSAGHHLTDLGHHGNDSGVTGLGHHDTDYDERRGKGFEDPIDNKTGLGSDYDTTETGSGYGATDTGAAPHKKGIITKIKEKLHH", translationNoStop);
        String translationStop = SequenceUtil.translateSequence(nucleotides, SequenceUtil.getTranslationTableForGeneticCode(1), true, false);
        logger.info("translation (stop): " + translationStop);
        assertEquals("translation (stop): ", "MNQYGREQQDTGLVGSGTGHRDEYGNPRQEGIMDKVKNAVGMGPSSGTGYNNQPGYDNYGNPRQEGLVDKAKDAVGMGPSLGTGYNNQPGYDSYGNREGIVDRAKDAVGMGPNSGTGYNNQPGYDNYGDRRHEGLADRAKDAVGMGPNSGYNHQPGYDNYGNREGVVDKAKDAVGMGPNSGTGYNNQPGYDSYGTRRQEGLVDRAKDAVGMGPNSGTGYNNQPGYDNYGNPRREGVVDRAKDAVGMGPNSGYNNQPGYDNYGNREGIVDKAKDAVGVGPHSGTGYHNQPSYDNYGNPRQEGIVDRAKDAVGMGPNSGTGYNNQSDYDSYGNPRHEGMLDKAKDDFDMGPNSGTGYNNRPGYDTYGDRKHEGIGDKVRDAIGTGPNSGYDSRTPTGTDAYVHGNHPPGMQDRITGVNEPSILGGRENVDRHGFGHDGRQHHGLLDNVTLQSGHIPETMVGGRRVEPGYDMTKSAGHHLTDLGHHGNDSGVTGLGHHDTDYDERRGKGFEDPIDNKTGLGSDYDTTETGSGYGATDTGAAPHKKGIITKIKEKLHH*", translationStop);
        String translationMultipleStops = SequenceUtil.translateSequence(nucleotides.substring(2), SequenceUtil.getTranslationTableForGeneticCode(1), true, true);
        logger.info("translation (multiple stops): " + translationMultipleStops);
        assertEquals("tranlsation (multiple stops): ", "ESVRKRTARYWPRRLWYRTSR*IRQSQARGYNGQGEKCRRHGPQFRNRLQQSAWL*QLR*PKARRISRQGEGRRGHGSEFRNWLQ*PAWL*QLRES*GHCGQGERCGRDGSEFRNWLQQPAWVRQLR*PKA*RIGRQSEGCCRYGA*LRLQPPAWI*QLRQS*GRCGQGEGCGRHGSEFRNWLQQPAWL*QLWYPETGRIGR*SKGCRRHGPQFGHRL*QSARI*QLR*PKTRRSGRQGEGCCRYGA*LRLQQSARI*QLRQS*GHCGQSQGCSRCWPPLGYWLPQPAQLRQLWQP*ARGNRG*SERRCGDGTKLWNWLQQPV*L*QLWQPKARRHA*QGEG*L*YGPQFRHWL*QPARL*HLWGPKTRGNW*QGEGRNRYWPKLRI*QPHTHRNRRLRAWQPSPWYARQNHWRERALDLRWT*ECRPPWFWTRWSPTSRSAR*CYSSKWPYS*DYGRRAPC*TWI*YDQECWTSSY*SWPSR*R*RCHWIGPSRH*LR*EEGKRI*RPD**QNRTWIRLRYDRDRIWLWCHRYWCCTSQEGNHN*DQGEAAPL", translationMultipleStops);
    }
    
}
