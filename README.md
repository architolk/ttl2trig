# ttl2trig
Creates trig files from turtle files, using provenance

Usage:

- Input file can be a turtle file (.ttl), but might also be another RDF format
- Output file can be a trig file (.trig), but might also be another quad RDF format (JSON-LD, NQuads, etc)
- One or two sparql update query files should be given, to transfer the triples from the input file to the corresponding named graphs
- If no sparql updat query is given, the resulting file will have one named graph, called <urn:input>

##Example query.sparql:

This example query will transfer all resource to individual graphs with the same URI as the resource itself

```
DELETE {
  GRAPH <urn:input> {
    ?s?p?o
  }
}
INSERT {
  GRAPH ?s {
    ?s?p?o
  }
}
WHERE {
  GRAPH <urn:input> {
    ?s?p?o
  }
}
```
