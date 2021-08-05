package nl.architolk.ttl2trig;

import java.io.FileOutputStream;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import org.apache.jena.riot.RDFLanguages;
import org.apache.jena.update.UpdateAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Convert {

  private static final Logger LOG = LoggerFactory.getLogger(Convert.class);

  public static void main(String[] args) {

    if (args.length > 1) {

      LOG.info("Starting conversion");
      LOG.info("Input file: {}",args[0]);
      LOG.info("Ouput file: {}",args[1]);
      if (args.length == 3) {
        LOG.info("Query file: {}",args[2]);
      }
      if (args.length == 4) {
        LOG.info("Query file #1: {}",args[2]);
        LOG.info("Query file #2: {}",args[3]);
      }

      try {
        LOG.info("- reading file");
        Model model = RDFDataMgr.loadModel(args[0]);
        Dataset dataset = DatasetFactory.create();
        dataset.addNamedModel("urn:input",model);

        UpdateAction action = new UpdateAction();
        if (args.length > 2) {
          LOG.info("- execute query #1");
          action.readExecute(args[2],dataset);
        }
        if (args.length == 4) {
          LOG.info("- execute query #2");
          action.readExecute(args[3],dataset);
        }

        RDFDataMgr.write(new FileOutputStream(args[1]),dataset, RDFLanguages.filenameToLang(args[1],RDFLanguages.TRIG));
        LOG.info("Done!");
      }
      catch (Exception e) {
        LOG.error(e.getMessage(),e);
      }
    } else {
      LOG.info("Usage: ttl2trig <input.ttl> <output.trig> [query.sparql]*");
    }
  }
}
