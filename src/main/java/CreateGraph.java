import java.util.*;

/**
 * Created by sinaaskarnejad on 4/12/16.
 */
public class CreateGraph {
    public void graphCreator(int[] CostWithExternalServices, int[][] interactions, int[] CostWithInternalServices) {
        Node s = new Node(-4);
        Node ns = new Node(-3);
        int internalSum = 0;
        int externalSum = 0;
        Node[] internals = new Node[CostWithExternalServices.length];
        Node[] extrenals = new Node[CostWithExternalServices.length];
        Vector<Integer> ids = new Vector<Integer>(CostWithExternalServices.length);
        int[][] icosts = new int[CostWithExternalServices.length][CostWithInternalServices.length];
        int[][] icosts2 = new int[CostWithExternalServices.length][CostWithInternalServices.length];
        Vector<Link> links = new Vector<Link>();
        Node bt = new Node(-2);
        int linknumber = 0;
        Random r = new Random();
        for (int i = 0; i < CostWithExternalServices.length; i++) {
            internalSum += CostWithExternalServices[i];
            Node internal = new Node(i);
            internals[i] = internal;
            ids.add(i);
            ns.forwardNodes.put(internals[i], new Integer[]{CostWithExternalServices[i]});
            Link l = new Link(ns, internals[i], CostWithExternalServices[i]);
            linknumber++;
            links.add(l);
            ns.forwardLinks.put(internals[i], new Link[]{l});
            internals[i].fathers.add(ns);

        }
        s.forwardNodes.put(ns, new Integer[]{internalSum});
        links.add(new Link(s, ns, internalSum));
        linknumber++;
        s.forwardLinks.put(ns, new Link[]{links.lastElement()});
        ns.fathers.add(s);
        for (int i = 0; i < CostWithInternalServices.length; i++) {
            externalSum += CostWithInternalServices[i];
            Node external = new Node(i);
            extrenals[i] = external;
            external.forwardNodes.put(bt, new Integer[]{CostWithInternalServices[i]});
            links.add(new Link(external, bt, CostWithInternalServices[i]));
            linknumber++;
            external.forwardLinks.put(bt, new Link[]{links.lastElement()});
            bt.fathers.add(external);
        }

        Node t = new Node(-1);
        bt.forwardNodes.put(t, new Integer[]{externalSum});
        links.add(new Link(bt, t, externalSum));
        linknumber++;
        bt.forwardLinks.put(t, new Link[]{links.lastElement()});

        t.fathers.add(bt);
        int nodeId = CostWithExternalServices.length;
        for (int i = 0; i < interactions.length; i++) {
            for (int j = i; j < interactions[i].length; j++) {
                if (i == j && interactions[i][j] == 1) {
                    internals[i].interactingNodes.add(internals[j]);
                    extrenals[j].interactingNodes.add(extrenals[i]);
                    Node cl = new Node(nodeId++);
                    Node cr = new Node(nodeId++);
                    internals[i].forwardNodes.put(cl, new Integer[]{Integer.MAX_VALUE});
                    //  links.add(new Link(internals[i], cl, Integer.MAX_VALUE));
                    //  linknumber++;
                    internals[i].forwardLinks.put(cl, new Link[]{new Link(internals[i], cl, Integer.MAX_VALUE)});

                    cl.fathers.add(internals[i]);

                    cl.forwardNodes.put(cr, new Integer[]{Integer.MAX_VALUE});
                    links.add(new Link(cl, cr, Integer.MAX_VALUE));
                    linknumber++;
                    cl.forwardLinks.put(cr, new Link[]{links.lastElement()});
                    cr.fathers.add(cl);

                    cr.forwardNodes.put(extrenals[i], new Integer[]{Integer.MAX_VALUE});
                    //links.add(new Link(cr, extrenals[i], Integer.MAX_VALUE));
                    //   linknumber++;
                    cr.forwardLinks.put(extrenals[i], new Link[]{new Link(cr, extrenals[i], Integer.MAX_VALUE)});
                    extrenals[i].fathers.add(cr);

                } else if (interactions[i][j] == 1) {
                    internals[i].interactingNodes.add(internals[j]);
                    internals[j].interactingNodes.add(internals[i]);
                    extrenals[i].interactingNodes.add(extrenals[j]);
                    extrenals[j].interactingNodes.add(extrenals[i]);
                    Node cl = new Node(nodeId++);
                    internals[i].forwardNodes.put(cl, new Integer[]{Integer.MAX_VALUE});
                    internals[j].forwardNodes.put(cl, new Integer[]{Integer.MAX_VALUE});
                    //   links.add(new Link(internals[i], cl, Integer.MAX_VALUE));
                    //   linknumber++;
                    internals[i].forwardLinks.put(cl, new Link[]{new Link(internals[i], cl, Integer.MAX_VALUE)});
                    //   links.add(new Link(internals[j], cl, Integer.MAX_VALUE));
                    //   linknumber++;
                    internals[j].forwardLinks.put(cl, new Link[]{new Link(internals[j], cl, Integer.MAX_VALUE)});

                    cl.fathers.add(internals[i]);
                    cl.fathers.add(internals[j]);
                    Node cr = new Node(nodeId++);
                    int xxx=r.nextInt(10) + 1;
                    int yyy=r.nextInt(10) + 1;
                    int cost1 = CostWithExternalServices[i] + CostWithInternalServices[j] + xxx;
                    int cost2 = CostWithExternalServices[j] + CostWithInternalServices[i] + yyy;
                    icosts[i][j] = cost1;
                    icosts[j][i] = cost2;
                    icosts2[i][j]=xxx;
                    icosts2[j][i]=yyy;
                    cl.forwardNodes.put(cr, new Integer[]{cost1, cost2});
                    links.add(new Link(cl, cr, cost1, false));
                    linknumber++;
                    links.add(new Link(cl, cr, cost2, true));
                    linknumber++;
                    cl.forwardLinks.put(cr, new Link[]{links.get(linknumber - 2), links.get(linknumber - 1)});
                    cr.fathers.add(cl);

                    cr.forwardNodes.put(extrenals[i], new Integer[]{Integer.MAX_VALUE});
                    cr.forwardNodes.put(extrenals[j], new Integer[]{Integer.MAX_VALUE});
                    //links.add(new Link(cr, extrenals[i], Integer.MAX_VALUE));
                    // linknumber++;
                    cr.forwardLinks.put(extrenals[i], new Link[]{new Link(cr, extrenals[i], Integer.MAX_VALUE)});
                    // links.add(new Link(cr, extrenals[j], Integer.MAX_VALUE));
                    //  linknumber++;
                    cr.forwardLinks.put(extrenals[j], new Link[]{new Link(cr, extrenals[j], Integer.MAX_VALUE)});

                    extrenals[i].fathers.add(cr);
                    extrenals[j].fathers.add(cr);

                }
            }
            internals[i].createConnection();
            extrenals[i].createConnection();
        }

        //graph created
        boolean[] resultin=new boolean[CostWithExternalServices.length];
        boolean[] resultout=new boolean[CostWithInternalServices.length];
        int maximumOfInteraction = 0;
        int interactionSelected = 0;
        Vector<ResultLink> notused = new Vector<ResultLink>();
        Vector<ResultLink> addStepBystep = new Vector<ResultLink>();
        boolean addstepbystep = false;
        Vector<ResultNode> newcreatedobjects = new Vector<ResultNode>();
        Vector<Link> templinks = new Vector<Link>();
        Vector<Link> removedLinks = new Vector<Link>();
        boolean mustcopy = true;
        int notusedCounter = 0;
        boolean[][] interactionisselected = new boolean[CostWithExternalServices.length][CostWithExternalServices.length];
        for (int i = 0; i < CostWithExternalServices.length; i++) {
            for (int j = i + 1; j < CostWithExternalServices.length; j++) {
                if (interactions[i][j] == 1) {
                    maximumOfInteraction++;
                }
            }
        }
        boolean[] Allchoosed = new boolean[CostWithExternalServices.length];
        boolean[] AllchoosedTemp = new boolean[CostWithExternalServices.length];
        int countChoosednumber = 0;
        int subtractChoosednumber = 0;
        int valueOfsourcesubtracted = 0;
        int valueOfdestinationsubtracted = 0;
        HashMap<Link, Integer> templinkvalues = new HashMap<Link, Integer>();
        ResultNode[] rni = new ResultNode[CostWithExternalServices.length];
        ResultNode[] rne = new ResultNode[CostWithExternalServices.length];
        ResultNode startrn = new ResultNode(-5, -1);
        ResultNode n0rni = new ResultNode(0, 1);
        ResultNode n0rne = new ResultNode(0, 2);
        startrn.addConnection(n0rni);
        startrn.addConnection(n0rne);
        n0rne.temp = true;
        n0rni.temp = true;
        boolean answerfound = false;
        boolean onetimefinished = false;
        boolean onechoosed = false;
        Object lastlink = null;
        Link linkfound = null;
        boolean deletefoundedlink = false;
        boolean backtoDefault = false;
        boolean copyonce = true;
        boolean checkfirsttime = true;
        while (true) {
            if (onetimefinished && copyonce) {
                AllchoosedTemp = Arrays.copyOf(Allchoosed, Allchoosed.length);
                copyonce = false;
            }

            if (countChoosednumber >= CostWithExternalServices.length) {
                System.out.println("called for checking answer");
                resultin=new boolean[CostWithExternalServices.length];
                resultout=new boolean[CostWithExternalServices.length];
                boolean[] tempchoosed = new boolean[CostWithExternalServices.length];
                int tempCounter = 0;
                for (ResultNode rn : rni) {
                    if (rn != null) {
                        int mustHaveNConnections = internals[rn.id].interactingNodes.size();
                        boolean[] interations = new boolean[CostWithExternalServices.length];
                        int haveconnectionTillKnow = 0;
                        for (Node nnn : internals[rn.id].interactingNodes) {
                            if (rn.connections.get(rni[nnn.id]) != null && rni[nnn.id].choosed && !interations[nnn.id]) {
                                interations[nnn.id] = true;
                                haveconnectionTillKnow++;
                            }
                            if (rn.connections.get(rne[nnn.id]) != null && rne[nnn.id].choosed && !interations[nnn.id]) {
                                interations[nnn.id] = true;
                                haveconnectionTillKnow++;
                            }
                        }
                        if (haveconnectionTillKnow >= mustHaveNConnections) {
                            resultin[rn.id]=true;
                            if (!tempchoosed[rn.id]) {
                                tempCounter++;
                            }
                            tempchoosed[rn.id] = true;
                        }
                    }
                }
                for (ResultNode rn : rne) {
                    if (rn != null) {
                        int mustHaveNConnections = extrenals[rn.id].interactingNodes.size();
                        boolean[] interations = new boolean[CostWithExternalServices.length];
                        int haveconnectionTillKnow = 0;

                        for (Node nnn : extrenals[rn.id].interactingNodes) {
                            if (rn.connections.get(rne[nnn.id]) != null && rne[nnn.id].choosed && !interations[nnn.id]) {
                                interations[nnn.id] = true;
                                haveconnectionTillKnow++;
                            }
                            if (rn.connections.get(rni[nnn.id]) != null && rni[nnn.id].choosed && !interations[nnn.id]) {
                                interations[nnn.id] = true;
                                haveconnectionTillKnow++;
                            }
                        }
                        if (haveconnectionTillKnow >= mustHaveNConnections) {
                            resultout[rn.id]=true;
                            if (!tempchoosed[rn.id]) {
                                tempCounter++;
                            }
                            tempchoosed[rn.id] = true;
                        }
//                    boolean[] countconnected=new boolean[internals[rn.id].interactingNodes.size()];
                    }
                }
                //check if all choosed
                boolean finish = false;
                if (tempCounter == CostWithExternalServices.length) {
                    finish = true;
                }
                if (finish && checkfirsttime) {
                    break;
                    //we have answer
                }
                if (finish) {
                    //change to default and add last link
                    backtoDefault = true;
                    addstepbystep = false;
                    countChoosednumber -= subtractChoosednumber;
                    subtractChoosednumber = 0;
                    notusedCounter = 0;
                    onechoosed = false;
                    addStepBystep.clear();
                    Allchoosed = Arrays.copyOf(AllchoosedTemp, AllchoosedTemp.length);
                    mustcopy = true;
                    for (Link ll : links) {
                        ll.value = templinkvalues.get(ll);
                    }
                    for (Link ll : removedLinks) {
                        ll.value = templinkvalues.get(ll);
                    }
                    templinkvalues.clear();
                    deletefoundedlink = false;
                    for (Link lll : links) {
                        if (lastlink instanceof Link) {
                            if (lll.start == ((Link) lastlink).start && lll.end == ((Link) lastlink).end && lll.big == ((Link) lastlink).big) {
                                links.remove(lll);
                                linkfound = lll;
                                deletefoundedlink = true;
                                break;
                            }

                        } else {
                            if (((ResultLink) lastlink).type == 1) {
                                if (lll.end == ((ResultLink) lastlink).ntwo) {
                                    links.remove(lll);
                                    linkfound = lll;
                                    deletefoundedlink = true;
                                    break;
                                }
                            } else if (((ResultLink) lastlink).type == 2) {
                                int id0 = lll.start.fathers.get(0).id;
                                int id1 = lll.start.fathers.get(1).id;
                                if (lll.big) {
                                    if (internals[id1] == ((ResultLink) lastlink).none && extrenals[id0] == ((ResultLink) lastlink).ntwo) {
                                        links.remove(lll);
                                        linkfound = lll;
                                        deletefoundedlink = true;
                                        break;
                                    }
                                } else {
                                    if (internals[id0] == ((ResultLink) lastlink).none && extrenals[id1] == ((ResultLink) lastlink).ntwo) {
                                        links.remove(lll);
                                        linkfound = lll;
                                        deletefoundedlink = true;
                                        break;
                                    }
                                }
                            } else {
                                if (lll.start == ((ResultLink) lastlink).none) {
                                    links.remove(lll);
                                    linkfound = lll;
                                    deletefoundedlink = true;
                                    break;
                                }
                            }
                        }
                    }
                    for (ResultNode rnn : newcreatedobjects) {
                        if (rnn.type == 1) {
                            rni[rnn.id] = null;
                        } else {
                            rne[rnn.id] = null;
                        }
                    }
                    for (ResultNode rnn : rni) {
                        if (rnn != null && rnn.addtotemp) {
                            if (rnn.tempchoosed) {
                                rnn.tempchoosed = false;
                                rnn.choosed = false;
                            }
                            Iterator<Map.Entry<ResultNode, Integer>> it = rnn.connections.entrySet().iterator();
                            while (it.hasNext()) {
                                Map.Entry<ResultNode, Integer> tempentry = it.next();
                                if (rnn.tempconnections.get(tempentry.getKey()) != null) {
                                    it.remove();
                                }
                            }
                            rnn.tempconnections.clear();
                            rnn.addtotemp = false;

                        }
                    }

                    for (ResultNode rnn : rne) {
                        if (rnn != null && rnn.addtotemp) {
                            if (rnn.tempchoosed) {
                                rnn.tempchoosed = false;
                                rnn.choosed = false;
                            }
                            Iterator<Map.Entry<ResultNode, Integer>> it = rnn.connections.entrySet().iterator();
                            while (it.hasNext()) {
                                Map.Entry<ResultNode, Integer> tempentry = it.next();
                                if (rnn.tempconnections.get(tempentry.getKey()) != null) {
                                    it.remove();
                                }
                            }
                            rnn.tempconnections.clear();
                            rnn.addtotemp = false;
                        }
                    }
                    for (Node rnn : internals) {
                        if (rnn.addtotemp) {
                            for (int i = 0; i < rnn.tempinteraction[1].length; i++) {
                                if (rnn.tempinteraction[1][i] == 1) {
                                    rnn.tempinteraction[1][i] = 0;
                                    rnn.interactions2[1][i] = 0;
                                }
                            }
                            rnn.addtotemp = false;
                            rnn.sum -= rnn.subtract;
                            rnn.subtract = 0;
                        }
                    }
                    for (Node rnn : extrenals) {
                        if (rnn.addtotemp) {
                            for (int i = 0; i < rnn.tempinteraction[1].length; i++) {
                                if (rnn.tempinteraction[1][i] == 1) {
                                    rnn.tempinteraction[1][i] = 0;
                                    rnn.interactions2[1][i] = 0;
                                }
                            }
                            rnn.addtotemp = false;
                            rnn.sum -= rnn.subtract;
                            rnn.subtract = 0;
                        }
                    }
                    valueOfsourcesubtracted = 0;
                    valueOfdestinationsubtracted = 0;
                } else {
                    checkfirsttime = false;
                    //continue
                }
            }
            while ((interactionSelected < maximumOfInteraction && !answerfound) || onetimefinished) {
                char[] places=new char[CostWithExternalServices.length];

                computeCostsBruteForce(places.length , places , interactions , icosts2 , CostWithExternalServices , CostWithInternalServices);

                if (deletefoundedlink) {
                    checkfirsttime = true;
                    Link ll = linkfound;
                    //******think must find likly links
                    ll.value = 0;
                    boolean start = false;
                    boolean end = false;
                    if (ll.start != ns) {
                        start = true;
                    }
                    if (ll.end != bt) {
                        end = true;
                    }
                    if (start && end) {
                        int id0 = ll.start.fathers.get(0).id;
                        int id1 = ll.start.fathers.get(1).id;
                        if (!interactionisselected[id0][id1] && !interactionisselected[id1][id0]) {
                            interactionisselected[id0][id1] = true;
                            interactionSelected++;
                        }
                        if (ll.big) {
                            if (rni[id1] == null) {
                                if (id1 == n0rni.id) {
                                    n0rni.temp = false;
                                    rni[id1] = n0rni;
                                } else {
                                    rni[id1] = new ResultNode(id1, 1);
                                }
                            }
                            if (rne[id0] == null) {
                                if (id0 == n0rne.id) {
                                    n0rne.temp = false;
                                    rne[id0] = n0rne;
                                } else {
                                    rne[id0] = new ResultNode(id0, 2);
                                }
                            }
                            rni[id1].addConnection(rne[id0]);
                            rne[id0].addConnection(rni[id1]);
                            internals[id1].addsum(id0);
                            extrenals[id0].addsum(id1);

                            if (!rni[id1].choosed) {
                                if (internals[id1].checking2()) {
                                    rni[id1].changeChoosed(true);
                                    if (!Allchoosed[id1]) {
                                        Allchoosed[id1] = true;
                                        countChoosednumber++;
                                    }
                                }
                            }

                            if (!rne[id0].choosed) {
                                if (extrenals[id0].checking2()) {
                                    rne[id0].changeChoosed(true);
                                    if (!Allchoosed[id0]) {
                                        Allchoosed[id0] = true;
                                        countChoosednumber++;
                                    }
                                }
                            }
                        } else {
                            if (rni[id0] == null) {
                                if (id0 == n0rni.id) {
                                    n0rni.temp = false;
                                    rni[id0] = n0rni;
                                } else {
                                    rni[id0] = new ResultNode(id0, 1);
                                }
                            }
                            if (rne[id1] == null) {
                                if (id1 == n0rne.id) {
                                    n0rne.temp = false;
                                    rne[id1] = n0rne;
                                } else {
                                    rne[id1] = new ResultNode(id1, 2);
                                }
                            }
                            rni[id0].addConnection(rne[id1]);
                            rne[id1].addConnection(rni[id0]);
                            internals[id0].addsum(id1);
                            extrenals[id1].addsum(id0);
                            if (!rni[id0].choosed) {
                                if (internals[id0].checking2()) {
                                    rni[id0].changeChoosed(true);
                                    if (!Allchoosed[id0]) {
                                        Allchoosed[id0] = true;
                                        countChoosednumber++;
                                    }
                                }
                            }
                            if (!rne[id1].choosed) {
                                if (extrenals[id1].checking2()) {
                                    rne[id1].changeChoosed(true);
                                    if (!Allchoosed[id1]) {
                                        Allchoosed[id1] = true;
                                        countChoosednumber++;
                                    }
                                }
                            }
                        }


//
                    } else if (end) {
                        int id0 = ll.end.id;
                        if (rni[id0] == null) {
                            if (id0 == n0rni.id) {
                                n0rni.temp = false;
                                rni[id0] = n0rni;

                            } else {
                                rni[id0] = new ResultNode(id0, 1);
                            }
                            rni[id0].star = true;
                        }
                        for (int i = 0; i < internals[id0].interactions2[0].length; i++) {
                            int temp = internals[id0].interactions2[0][i];
                            if (rni[temp] != null && rni[temp].star != false) {
                                rni[id0].addConnection(rni[temp]);
                                rni[temp].addConnection(rni[id0]);
                                internals[id0].addsum(temp);
                                internals[temp].addsum(id0);
                                if (!interactionisselected[id0][temp] && !interactionisselected[temp][id0]) {
                                    interactionisselected[id0][temp] = true;
                                    interactionSelected++;
                                }
                                if (!rni[temp].choosed) {
                                    if (internals[temp].checking2()) {
                                        rni[temp].changeChoosed(true);
                                        if (!Allchoosed[temp]) {
                                            Allchoosed[temp] = true;
                                            countChoosednumber++;
                                        }
                                    }
                                }

                            }
                        }
                        if (!rni[id0].choosed) {
                            if (internals[id0].checking2()) {
                                rni[id0].changeChoosed(true);
                                if (!Allchoosed[id0]) {
                                    Allchoosed[id0] = true;
                                    countChoosednumber++;
                                }
                            }
                        }

                    } else if (start) {
                        int id1 = ll.start.id;
                        if (rne[id1] == null) {
                            if (id1 == n0rne.id) {
                                n0rne.temp = false;
                                rne[id1] = n0rne;
                            } else {
                                rne[id1] = new ResultNode(id1, 2);
                            }
                            rne[id1].star = true;
                        }
                        for (int i = 0; i < extrenals[id1].interactions2[0].length; i++) {
                            int temp = extrenals[id1].interactions2[0][i];
                            if (rne[temp] != null && rne[temp].star != false) {
                                rne[id1].addConnection(rne[temp]);
                                rne[temp].addConnection(rne[id1]);
                                extrenals[id1].addsum(temp);
                                extrenals[temp].addsum(id1);
                                if (!interactionisselected[id1][temp] && !interactionisselected[temp][id1]) {
                                    interactionisselected[id1][temp] = true;
                                    interactionSelected++;
                                }
                                if (!rne[temp].choosed) {
                                    if (extrenals[temp].checking2()) {
                                        rne[temp].changeChoosed(true);
                                        if (!Allchoosed[temp]) {
                                            Allchoosed[temp] = true;
                                            countChoosednumber++;
                                        }
                                    }
                                }

                            }
                        }
                        if (!rne[id1].choosed) {
                            if (extrenals[id1].checking2()) {
                                rne[id1].changeChoosed(true);
                                if (!Allchoosed[id1]) {
                                    Allchoosed[id1] = true;
                                    countChoosednumber++;
                                }
                            }
                        }

                    }
                    AllchoosedTemp = Arrays.copyOf(Allchoosed, Allchoosed.length);
                    deletefoundedlink = false;
                    break;
                }
                if (onetimefinished && mustcopy) {
                    for (Link lll : links) {
                        int temp = lll.value;
                        templinkvalues.put(lll, temp);
                    }
                    mustcopy = false;
                }
                if (onetimefinished && onechoosed) {
                    onechoosed = false;
                    break;
                } else if (onetimefinished && notusedCounter < notused.size()) {
                    ResultLink ll = notused.get(notusedCounter++);
                    lastlink = ll;
                    //check what happen if notused added
                    if (ll.type == 2) {
                        int id0 = ll.none.id;
                        int id1 = ll.ntwo.id;
                        if (rni[id0] == null) {
                            if (id0 == n0rni.id) {
                                n0rni.temp = false;
                                rni[id0] = n0rni;
                            } else {
                                rni[id0] = new ResultNode(id0, 1);
                            }
                            newcreatedobjects.add(rni[id0]);
                        }
                        if (rne[id1] == null) {
                            if (id1 == n0rne.id) {
                                n0rne.temp = false;
                                rne[id1] = n0rne;
                            } else {
                                rne[id1] = new ResultNode(id1, 2);
                            }
                            newcreatedobjects.add(rne[id1]);
                        }
                        rni[id0].addConnection(rne[id1]);
                        rne[id1].addConnection(rni[id0]);
                        rni[id0].addTempConnection(rne[id1]);
                        rne[id1].addTempConnection(rni[id0]);
                        internals[id0].addtotemp = true;
                        extrenals[id1].addtotemp = true;
                        internals[id0].addsum(id1);
                        extrenals[id1].addsum(id0);
                        if (!rni[id0].choosed) {
                            if (internals[id0].checking2()) {
                                rni[id0].addtotemp = true;
                                rni[id0].changeChoosed(true);
                                if (!Allchoosed[id0]) {
                                    Allchoosed[id0] = true;
                                    countChoosednumber++;
                                    subtractChoosednumber++;
                                }
                            }
                        }
                        if (!rne[id1].choosed) {
                            if (extrenals[id1].checking2()) {
                                rne[id1].addtotemp = true;
                                rne[id1].changeChoosed(true);
                                if (!Allchoosed[id1]) {
                                    Allchoosed[id1] = true;
                                    countChoosednumber++;
                                    subtractChoosednumber++;
                                }
                            }
                        }
                    } else if (ll.type == 1) {
                        int id0 = ll.none.id;
                        if (rni[id0] == null) {
                            if (id0 == n0rni.id) {
                                n0rni.temp = false;
                                rni[id0] = n0rni;

                            } else {
                                rni[id0] = new ResultNode(id0, 1);
                            }
                            rni[id0].star = true;
                            newcreatedobjects.add(rni[id0]);
                        }
                        for (int i = 0; i < internals[id0].interactions2[0].length; i++) {
                            int temp = internals[id0].interactions2[0][i];
                            if (rni[temp] != null && rni[temp].star) {
                                rni[id0].addConnection(rni[temp]);
                                rni[temp].addConnection(rni[id0]);
                                rni[id0].addTempConnection(rni[temp]);
                                rni[temp].addTempConnection(rni[id0]);
                                internals[id0].addtotemp = true;
                                internals[temp].addtotemp = true;
                                internals[id0].addsum(temp);
                                internals[temp].addsum(id0);
                                if (!rni[temp].choosed) {
                                    if (internals[temp].checking2()) {
                                        rni[temp].addtotemp = true;
                                        rni[temp].changeChoosed(true);
                                        if (!Allchoosed[temp]) {
                                            Allchoosed[temp] = true;
                                            countChoosednumber++;
                                            subtractChoosednumber++;
                                        }
                                    }
                                }

                            }
                        }
                        if (!rni[id0].choosed) {
                            if (internals[id0].checking2()) {
                                rni[id0].addtotemp = true;
                                rni[id0].changeChoosed(true);
                                if (!Allchoosed[id0]) {
                                    Allchoosed[id0] = true;
                                    countChoosednumber++;
                                    subtractChoosednumber++;
                                }
                            }
                        }

                    } else if (ll.type == 3) {
                        int id1 = ll.ntwo.id;
                        if (rne[id1] == null) {
                            if (id1 == n0rne.id) {
                                n0rne.temp = false;
                                rne[id1] = n0rne;
                            } else {
                                rne[id1] = new ResultNode(id1, 2);
                            }
                            rne[id1].star = true;
                            newcreatedobjects.add(rne[id1]);
                        }
                        for (int i = 0; i < extrenals[id1].interactions2[0].length; i++) {
                            int temp = extrenals[id1].interactions2[0][i];
                            if (rne[temp] != null && rne[temp].star != false) {
                                rne[id1].addConnection(rne[temp]);
                                rne[temp].addConnection(rne[id1]);
                                rne[id1].addTempConnection(rne[temp]);
                                rne[temp].addTempConnection(rne[id1]);
                                extrenals[id1].addtotemp = true;
                                extrenals[temp].addtotemp = true;
                                extrenals[id1].addsum(temp);
                                extrenals[temp].addsum(id1);
                                if (!rne[temp].choosed) {
                                    if (extrenals[temp].checking2()) {
                                        rne[temp].addtotemp = true;
                                        rne[temp].changeChoosed(true);
                                        if (!Allchoosed[temp]) {
                                            Allchoosed[temp] = true;
                                            countChoosednumber++;
                                            subtractChoosednumber++;
                                        }
                                    }
                                }

                            }
                        }
                        if (!rne[id1].choosed) {
                            if (extrenals[id1].checking2()) {
                                rne[id1].addtotemp = true;
                                rne[id1].changeChoosed(true);
                                if (!Allchoosed[id1]) {
                                    Allchoosed[id1] = true;
                                    countChoosednumber++;
                                    subtractChoosednumber++;
                                }
                            }
                        }

                    }
                    //finished what happen if notused added
                    break;
                } else if (onetimefinished && addstepbystep) {
                    addstepbystep = false;
                    break;
                } else if (onetimefinished && addStepBystep.size() > 0) {
                    ResultLink ll = addStepBystep.remove(0);
                    lastlink = ll;
                    //check what happen if notused added
                    if (ll.type == 2) {
                        int id0 = ll.none.id;
                        int id1 = ll.ntwo.id;
                        if (rni[id0] == null) {
                            if (id0 == n0rni.id) {
                                n0rni.temp = false;
                                rni[id0] = n0rni;
                            } else {
                                rni[id0] = new ResultNode(id0, 1);
                            }
                            newcreatedobjects.add(rni[id0]);
                        }
                        if (rne[id1] == null) {
                            if (id1 == n0rne.id) {
                                n0rne.temp = false;
                                rne[id1] = n0rne;
                            } else {
                                rne[id1] = new ResultNode(id1, 2);
                            }
                            newcreatedobjects.add(rne[id1]);
                        }
                        rni[id0].addConnection(rne[id1]);
                        rne[id1].addConnection(rni[id0]);
                        rni[id0].addTempConnection(rne[id1]);
                        rne[id1].addTempConnection(rni[id0]);
                        internals[id0].addtotemp = true;
                        extrenals[id1].addtotemp = true;
                        internals[id0].addsum(id1);
                        extrenals[id1].addsum(id0);
                        if (!rni[id0].choosed) {
                            if (internals[id0].checking2()) {
                                rni[id0].addtotemp = true;
                                rni[id0].changeChoosed(true);
                                if (!Allchoosed[id0]) {
                                    Allchoosed[id0] = true;
                                    countChoosednumber++;
                                    subtractChoosednumber++;
                                }
                            }
                        }
                        if (!rne[id1].choosed) {
                            if (extrenals[id1].checking2()) {
                                rne[id1].addtotemp = true;
                                rne[id1].changeChoosed(true);
                                if (!Allchoosed[id1]) {
                                    Allchoosed[id1] = true;
                                    countChoosednumber++;
                                    subtractChoosednumber++;
                                }
                            }
                        }
                    } else if (ll.type == 1) {
                        int id0 = ll.none.id;
                        if (rni[id0] == null) {
                            if (id0 == n0rni.id) {
                                n0rni.temp = false;
                                rni[id0] = n0rni;

                            } else {
                                rni[id0] = new ResultNode(id0, 1);
                            }
                            rni[id0].star = true;
                            newcreatedobjects.add(rni[id0]);
                        }
                        for (int i = 0; i < internals[id0].interactions2[0].length; i++) {
                            int temp = internals[id0].interactions2[0][i];
                            if (rni[temp] != null && rni[temp].star != false) {
                                rni[id0].addConnection(rni[temp]);
                                rni[temp].addConnection(rni[id0]);
                                rni[id0].addTempConnection(rni[temp]);
                                rni[temp].addTempConnection(rni[id0]);
                                internals[id0].addtotemp = true;
                                internals[temp].addtotemp = true;
                                internals[id0].addsum(temp);
                                internals[temp].addsum(id0);
                                if (!rni[temp].choosed) {
                                    if (internals[temp].checking2()) {
                                        rni[temp].addtotemp = true;
                                        rni[temp].changeChoosed(true);
                                        if (!Allchoosed[temp]) {
                                            Allchoosed[temp] = true;
                                            countChoosednumber++;
                                            subtractChoosednumber++;
                                        }
                                    }
                                }

                            }
                        }
                        if (!rni[id0].choosed) {
                            if (internals[id0].checking2()) {
                                rni[id0].addtotemp = true;
                                rni[id0].changeChoosed(true);
                                if (!Allchoosed[id0]) {
                                    Allchoosed[id0] = true;
                                    countChoosednumber++;
                                    subtractChoosednumber++;
                                }
                            }
                        }

                    } else if (ll.type == 3) {
                        int id1 = ll.ntwo.id;
                        if (rne[id1] == null) {
                            if (id1 == n0rne.id) {
                                n0rne.temp = false;
                                rne[id1] = n0rne;
                            } else {
                                rne[id1] = new ResultNode(id1, 2);
                            }
                            rne[id1].star = true;
                            newcreatedobjects.add(rne[id1]);
                        }
                        for (int i = 0; i < extrenals[id1].interactions2[0].length; i++) {
                            int temp = extrenals[id1].interactions2[0][i];
                            if (rne[temp] != null && rne[temp].star != false) {
                                rne[id1].addConnection(rne[temp]);
                                rne[temp].addConnection(rne[id1]);
                                rne[id1].addTempConnection(rne[temp]);
                                rne[temp].addTempConnection(rne[id1]);
                                extrenals[id1].addtotemp = true;
                                extrenals[temp].addtotemp = true;
                                extrenals[id1].addsum(temp);
                                extrenals[temp].addsum(id1);
                                if (!rne[temp].choosed) {
                                    if (extrenals[temp].checking2()) {
                                        rne[temp].addtotemp = true;
                                        rne[temp].changeChoosed(true);
                                        if (!Allchoosed[temp]) {
                                            Allchoosed[temp] = true;
                                            countChoosednumber++;
                                            subtractChoosednumber++;
                                        }
                                    }
                                }

                            }
                        }
                        if (!rne[id1].choosed) {
                            if (extrenals[id1].checking2()) {
                                rne[id1].addtotemp = true;
                                rne[id1].changeChoosed(true);
                                if (!Allchoosed[id1]) {
                                    Allchoosed[id1] = true;
                                    countChoosednumber++;
                                    subtractChoosednumber++;
                                }
                            }
                        }

                    }
                    //finished what happen if stepbystep added
                    break;
                } else {
                    onechoosed = false;
                    boolean wehaveend = false;
                    boolean wehavestart = false;
                    Link[] links1 = links.toArray(new Link[links.size()]);
                    Arrays.sort(links1);
                    for (int select = 0; select < links1.length; select++) {
                        System.out.println(links1[select]);
                        if (onechoosed) {
                            break;
                        }
                        if (links1[select].end == ns) {
                            System.out.println("all vms must be internal--> " + internalSum);
                            onechoosed = true;
                            answerfound = true;
                            onetimefinished = false;
                            break;
                        } else if (links1[select].start == bt) {
                            System.out.println("all vms must be external--> " + externalSum);
                            onechoosed = true;
                            answerfound = true;
                            onetimefinished = false;
                            break;
                        } else if (links1[select].start == ns) {
                            onechoosed = true;
                            Set<Link> pathes = new HashSet<Link>();
                            Set<Node> endNodes = new HashSet<Node>();
                            pathes.add(links1[select]);
                            for (Node n : links1[select].end.forwardLinks.keySet()) {
                                Collection<Link[]> midlinks = n.forwardLinks.values();
                                for (Link[] ls : midlinks) {
                                    for (int c = 0; c < ls.length; c++) {
                                        if (ls[c].value != 0) {
                                            pathes.add(ls[c]);
                                            endNodes.addAll(ls[c].end.forwardNodes.keySet());
                                        }
                                        if (ls[c].value != 0 && ls[c].value < links1[select].value) {
                                            System.out.println("cant subtract");
                                        }
                                    }
                                }
                            }

                            for (Node nn : endNodes) {

                                if (nn.forwardLinks.get(bt)[0].value >= links1[select].value) {
                                    pathes.add(nn.forwardLinks.get(bt)[0]);
                                    wehaveend = true;
                                    break;
                                }
                            }
                            int val = links1[select].value;
                            System.out.println("pathes" + pathes);
                            addstepbystep = false;
                            for (Link ll : pathes) {
                                boolean start = false;
                                boolean end = false;
                                ll.value -= val;
                                if (ll.value == 0) {
                                    if (ll.start != ns) {
                                        start = true;
                                        // selected.add(ll.start.id);
                                    }
                                    if (ll.end != bt) {
                                        end = true;
                                        // selected.add(ll.end.id);
                                    }
                                    if (start && end) {
                                        int id0 = ll.start.fathers.get(0).id;
                                        int id1 = ll.start.fathers.get(1).id;
                                        if (!onetimefinished && (interactionisselected[id0][id1] || interactionisselected[id1][id0])) {
                                            if (ll.big) {
                                                notused.add(new ResultLink(internals[id1], extrenals[id0], 2));
                                            } else {
                                                notused.add(new ResultLink(internals[id0], extrenals[id1], 2));
                                            }
                                            links.remove(ll);

                                            continue;
                                        }
                                        if (onetimefinished && addstepbystep) {
                                            if (ll.big) {
                                                addStepBystep.add(new ResultLink(internals[id1], extrenals[id0], 2));
                                            } else {
                                                addStepBystep.add(new ResultLink(internals[id0], extrenals[id1], 2));
                                            }
                                            links.remove(ll);
                                            continue;
                                        }
                                        if (!onetimefinished) {
                                            interactionisselected[id0][id1] = true;
                                            interactionSelected++;
                                        }
                                        if (ll.big) {
                                            if (rni[id1] == null) {
                                                if (id1 == n0rni.id) {
                                                    n0rni.temp = false;
                                                    rni[id1] = n0rni;
                                                } else {
                                                    rni[id1] = new ResultNode(id1, 1);
                                                }
                                                if (onetimefinished) {
                                                    newcreatedobjects.add(rni[id1]);
                                                }
                                            }
                                            if (rne[id0] == null) {
                                                if (id0 == n0rne.id) {
                                                    n0rne.temp = false;
                                                    rne[id0] = n0rne;
                                                } else {
                                                    rne[id0] = new ResultNode(id0, 2);
                                                }
                                                if (onetimefinished) {
                                                    newcreatedobjects.add(rne[id0]);
                                                }
                                            }
                                            rni[id1].addConnection(rne[id0]);
                                            rne[id0].addConnection(rni[id1]);
                                            if (onetimefinished) {
                                                rni[id1].addTempConnection(rne[id0]);
                                                rne[id0].addTempConnection(rni[id1]);
                                                internals[id1].addtotemp = true;
                                                extrenals[id0].addtotemp = true;
                                            }
                                            internals[id1].addsum(id0);
                                            extrenals[id0].addsum(id1);

                                            if (!rni[id1].choosed) {
                                                if (internals[id1].checking2()) {
                                                    if (onetimefinished) {
                                                        rni[id1].addtotemp = true;
                                                    }
                                                    rni[id1].changeChoosed(true);
                                                    if (!Allchoosed[id1]) {
                                                        Allchoosed[id1] = true;
                                                        if (onetimefinished) {
                                                            subtractChoosednumber++;
                                                        }
                                                        countChoosednumber++;
                                                    }
                                                }
                                            }

                                            if (!rne[id0].choosed) {
                                                if (extrenals[id0].checking2()) {
                                                    if (onetimefinished) {
                                                        rne[id0].addtotemp = true;
                                                    }
                                                    rne[id0].changeChoosed(true);
                                                    if (!Allchoosed[id0]) {
                                                        Allchoosed[id0] = true;
                                                        if (onetimefinished) {
                                                            subtractChoosednumber++;
                                                        }
                                                        countChoosednumber++;
                                                    }
                                                }
                                            }
                                        } else {
                                            if (rni[id0] == null) {
                                                if (id0 == n0rni.id) {
                                                    n0rni.temp = false;
                                                    rni[id0] = n0rni;
                                                } else {
                                                    rni[id0] = new ResultNode(id0, 1);
                                                }
                                                if (onetimefinished) {
                                                    newcreatedobjects.add(rni[id0]);
                                                }
                                            }
                                            if (rne[id1] == null) {
                                                if (id1 == n0rne.id) {
                                                    n0rne.temp = false;
                                                    rne[id1] = n0rne;
                                                } else {
                                                    rne[id1] = new ResultNode(id1, 2);
                                                }
                                                if (onetimefinished) {
                                                    newcreatedobjects.add(rne[id1]);
                                                }
                                            }
                                            rni[id0].addConnection(rne[id1]);
                                            rne[id1].addConnection(rni[id0]);
                                            if (onetimefinished) {
                                                rni[id0].addTempConnection(rne[id1]);
                                                rne[id1].addTempConnection(rni[id0]);
                                                internals[id0].addtotemp = true;
                                                extrenals[id1].addtotemp = true;
                                            }
                                            internals[id0].addsum(id1);
                                            extrenals[id1].addsum(id0);
                                            if (!rni[id0].choosed) {
                                                if (internals[id0].checking2()) {
                                                    if (onetimefinished) {
                                                        rni[id0].addtotemp = true;
                                                    }
                                                    rni[id0].changeChoosed(true);
                                                    if (!Allchoosed[id0]) {
                                                        Allchoosed[id0] = true;
                                                        if (onetimefinished) {
                                                            subtractChoosednumber++;
                                                        }
                                                        countChoosednumber++;
                                                    }
                                                }
                                            }
                                            if (!rne[id1].choosed) {
                                                if (extrenals[id1].checking2()) {
                                                    if (onetimefinished) {
                                                        rne[id1].addtotemp = true;
                                                    }
                                                    rne[id1].changeChoosed(true);
                                                    if (!Allchoosed[id1]) {
                                                        Allchoosed[id1] = true;
                                                        if (onetimefinished) {
                                                            subtractChoosednumber++;
                                                        }
                                                        countChoosednumber++;
                                                    }
                                                }
                                            }
                                        }


//
                                    } else if (end) {
                                        int id0 = ll.end.id;
                                        if (!onetimefinished) {
                                            boolean notexisted = false;
                                            for (int i = 0; i < internals[id0].interactions2[0].length; i++) {
                                                int temp = internals[id0].interactions2[0][i];
                                                if (!interactionisselected[id0][temp] && !interactionisselected[temp][id0]) {
                                                    notexisted = true;
                                                    break;
                                                }
                                            }
                                            if (!notexisted) {
                                                notused.add(new ResultLink(internals[id0], internals[id0], 1));
                                                links.remove(ll);
                                                continue;
                                            }
                                        }
                                        if (onetimefinished && addstepbystep) {

                                            addStepBystep.add(new ResultLink(internals[id0], internals[id0], 1));
                                            links.remove(ll);
                                            continue;

                                        }
                                        if (rni[id0] == null) {
                                            if (id0 == n0rni.id) {
                                                n0rni.temp = false;
                                                rni[id0] = n0rni;

                                            } else {
                                                rni[id0] = new ResultNode(id0, 1);
                                            }
                                            if (onetimefinished) {
                                                newcreatedobjects.add(rni[id0]);
                                            }

                                            rni[id0].star = true;
                                        }
                                        for (int i = 0; i < internals[id0].interactions2[0].length; i++) {
                                            int temp = internals[id0].interactions2[0][i];
                                            if (rni[temp] != null && rni[temp].star != false) {
                                                rni[id0].addConnection(rni[temp]);
                                                rni[temp].addConnection(rni[id0]);
                                                if (onetimefinished) {
                                                    rni[temp].addTempConnection(rni[id0]);
                                                    rni[id0].addTempConnection(rni[temp]);
                                                    internals[id0].addtotemp = true;
                                                    internals[temp].addtotemp = true;
                                                }
                                                internals[id0].addsum(temp);
                                                internals[temp].addsum(id0);
                                                if (!onetimefinished && !interactionisselected[id0][temp] && !interactionisselected[temp][id0]) {
                                                    interactionisselected[id0][temp] = true;
                                                    interactionSelected++;
                                                }
                                                if (!rni[temp].choosed) {
                                                    if (internals[temp].checking2()) {
                                                        if (onetimefinished) {
                                                            rni[temp].addtotemp = true;
                                                        }
                                                        rni[temp].changeChoosed(true);
                                                        if (!Allchoosed[temp]) {
                                                            Allchoosed[temp] = true;
                                                            if (onetimefinished) {
                                                                subtractChoosednumber++;
                                                            }
                                                            countChoosednumber++;
                                                        }
                                                    }
                                                }

                                            }
                                        }
                                        if (!rni[id0].choosed) {
                                            if (internals[id0].checking2()) {
                                                if (onetimefinished) {
                                                    rni[id0].addtotemp = true;
                                                }
                                                rni[id0].changeChoosed(true);
                                                if (!Allchoosed[id0]) {
                                                    Allchoosed[id0] = true;
                                                    if (onetimefinished) {
                                                        subtractChoosednumber++;
                                                    }
                                                    countChoosednumber++;
                                                }
                                            }
                                        }

                                    } else if (start) {
                                        int id1 = ll.start.id;
                                        if (!onetimefinished) {
                                            boolean notexisted = false;
                                            for (int i = 0; i < extrenals[id1].interactions2[0].length; i++) {
                                                int temp = extrenals[id1].interactions2[0][i];
                                                if (!interactionisselected[id1][temp] && !interactionisselected[temp][id1]) {
                                                    notexisted = true;
                                                    break;
                                                }
                                            }
                                            if (!notexisted) {
                                                notused.add(new ResultLink(extrenals[id1], extrenals[id1], 3));
                                                links.remove(ll);
                                                continue;
                                            }
                                        }
                                        if (onetimefinished && addstepbystep) {
                                            addStepBystep.add(new ResultLink(extrenals[id1], extrenals[id1], 3));
                                            links.remove(ll);
                                            continue;
                                        }
                                        if (rne[id1] == null) {
                                            if (id1 == n0rne.id) {
                                                n0rne.temp = false;
                                                rne[id1] = n0rne;
                                            } else {
                                                rne[id1] = new ResultNode(id1, 2);
                                            }
                                            rne[id1].star = true;
                                            if (onetimefinished) {
                                                newcreatedobjects.add(rne[id1]);
                                            }
                                        }
                                        for (int i = 0; i < extrenals[id1].interactions2[0].length; i++) {
                                            int temp = extrenals[id1].interactions2[0][i];
                                            if (rne[temp] != null && rne[temp].star != false) {
                                                rne[id1].addConnection(rne[temp]);
                                                rne[temp].addConnection(rne[id1]);
                                                if (onetimefinished) {
                                                    rne[id1].addConnection(rne[temp]);
                                                    rne[temp].addConnection(rne[id1]);
                                                    extrenals[id1].addtotemp = true;
                                                    extrenals[temp].addtotemp = true;
                                                }
                                                extrenals[id1].addsum(temp);
                                                extrenals[temp].addsum(id1);
                                                if (!onetimefinished && !interactionisselected[id1][temp] && !interactionisselected[temp][id1]) {
                                                    interactionisselected[id1][temp] = true;
                                                    interactionSelected++;
                                                }
                                                if (!rne[temp].choosed) {
                                                    if (extrenals[temp].checking2()) {
                                                        if (onetimefinished) {
                                                            rne[temp].addtotemp = true;
                                                        }
                                                        rne[temp].changeChoosed(true);
                                                        if (!Allchoosed[temp]) {
                                                            Allchoosed[temp] = true;
                                                            if (onetimefinished) {
                                                                subtractChoosednumber++;
                                                            }
                                                            countChoosednumber++;
                                                        }
                                                    }
                                                }

                                            }
                                        }
                                        if (!rne[id1].choosed) {
                                            if (extrenals[id1].checking2()) {
                                                if (onetimefinished) {
                                                    rne[id1].addtotemp = true;
                                                }
                                                rne[id1].changeChoosed(true);
                                                if (!Allchoosed[id1]) {
                                                    Allchoosed[id1] = true;
                                                    if (onetimefinished) {
                                                        subtractChoosednumber++;
                                                    }
                                                    countChoosednumber++;
                                                }
                                            }
                                        }

                                    }
                                    links.remove(ll);
                                    lastlink = ll;
                                    // ll.start.forwardLinks.
                                    if (onetimefinished) {
                                        addstepbystep = true;
                                    }

                                }
                                if (ll.value < 0) {
                                    System.out.println("error 1");
                                }
                            }
                            s.forwardLinks.get(ns)[0].value -= val;
                            if (onetimefinished) {
                                valueOfsourcesubtracted += val;
                            }
                            if (wehaveend) {
                                bt.forwardLinks.get(t)[0].value -= val;
                                if (onetimefinished) {
                                    valueOfdestinationsubtracted += val;
                                }
                            }
                            if (s.forwardLinks.get(ns)[0].value == 0) {
                                System.out.println("all vms must be internal--> " + internalSum);
                                onechoosed = true;
                                answerfound = true;
                                onetimefinished = false;
                                break;
                            } else if (bt.forwardLinks.get(t)[0].value == 0) {
                                System.out.println("all vms must be external--> " + externalSum);
                                onechoosed = true;
                                answerfound = true;
                                onetimefinished = false;
                                break;
                            }
                            System.out.println(Arrays.toString(rni));
                            System.out.println(Arrays.toString(rne));
                            for (int i = 0; i < icosts2.length; i++) {
                                System.out.println("-------------------");
                                for (int j = 0; j < icosts2[i].length; j++) {
                                    System.out.print(icosts2[i][j] + " ");
                                }
                                System.out.println();
                            }

                        } else if (links1[select].end == bt) {
                            System.out.println("called 2");
                            onechoosed = true;
                            Node starting = null;
                            Node last = links1[select].start;
                            int valu = links1[select].value;
                            Vector<Node> mr = last.fathers;
                            Vector<Node> ml = new Vector<Node>();
                            Vector<Node> ff = new Vector<Node>();
                            boolean choosedstart = false;
                            Node tempstart2 = null;
                            for (Node nmr : mr) {
                                Node left = nmr.fathers.get(0);
                                Link[] lis = left.forwardLinks.values().iterator().next();

                                boolean exist = true;
                                for (Link li : lis) {
                                    if (li.value < valu) {
                                        exist = false;
                                        break;
                                    }
                                }
                                if (exist) {
                                    for (Node no : left.fathers) {
                                        if (tempstart2 == null) {
                                            tempstart2 = no;
                                        }
                                        if (ns.forwardLinks.get(no)[0].value >= valu) {
                                            starting = no;
                                            wehavestart = true;
                                            choosedstart = true;
                                            break;

                                        }
                                    }
                                }
                                if (choosedstart) {
                                    break;
                                }
                            }
                            Node tempstart = null;
                            if (!choosedstart) {
                                for (Node nmr : mr) {

                                    Node left = nmr.fathers.get(0);
                                    Link[] lis = left.forwardLinks.values().iterator().next();
                                    boolean exist = true;
                                    int countzero = 0;
                                    for (Link li : lis) {
                                        if (!(li.value == 0 || li.value >= valu)) {
                                            exist = false;
                                            break;
                                        }
                                        if (li.value >= valu) {
                                            countzero++;
                                        }
                                    }
                                    if (exist && countzero > 0) {
                                        for (Node no : left.fathers) {
                                            if (tempstart == null) {
                                                tempstart = no;
                                            }
                                            if (ns.forwardLinks.get(no)[0].value >= valu) {
                                                starting = no;
                                                wehavestart = true;
                                                choosedstart = true;
                                                break;

                                            }
                                        }
                                    }
                                    if (choosedstart) {
                                        break;
                                    }
//                                    if (exist == true) {
//                                        for (Node no : left.fathers) {
//                                            if(tempstart==null){
//                                                tempstart = no;
//                                            }
//                                            if (ns.forwardLinks.get(no)[0].value >= valu) {
//                                                starting = no;
//                                                wehavestart=true;
//                                                choosedstart = true;
//                                                break;
//
//                                            }
//                                        }
//                                    }
//                                    if (choosedstart) {
//                                        break;
//                                    }
                                }

                            }
                            if (!wehavestart) {
                                if (tempstart2 != null) {
                                    starting = tempstart2;
                                } else if (tempstart != null) {
                                    starting = tempstart;
                                }
                            }
                            //find pathes
                            Set<Link> pathes = new HashSet<Link>();
                            if (starting != null) {
                                if (wehavestart) {
                                    pathes.add(ns.forwardLinks.get(starting)[0]);
                                }
                                for (Node n : starting.forwardLinks.keySet()) {
                                    Collection<Link[]> midlinks = n.forwardLinks.values();
                                    for (Link[] ls : midlinks) {
                                        for (int c = 0; c < ls.length; c++) {
                                            if (ls[c].value != 0) {
                                                pathes.add(ls[c]);
                                            }
                                            if (ls[c].value != 0 && ls[c].value < valu) {
                                                System.out.println("cant subtract 2");
                                            }
                                        }
                                    }
                                }
                            }
                            pathes.add(links1[select]);
                            // int val = links1[select].value;
                            System.out.println("pathes " + pathes);
                            addstepbystep = false;
                            for (Link ll : pathes) {
                                boolean start = false;
                                boolean end = false;
                                ll.value -= valu;
                                if (ll.value == 0) {
                                    if (ll.start != ns) {
                                        start = true;
                                        // selected.add(ll.start.id);
                                    }
                                    if (ll.end != bt) {
                                        end = true;
                                        // selected.add(ll.end.id);
                                    }
                                    if (start && end) {
                                        int id0 = ll.start.fathers.get(0).id;
                                        int id1 = ll.start.fathers.get(1).id;
                                        if (!onetimefinished && (interactionisselected[id0][id1] || interactionisselected[id1][id0])) {
                                            if (ll.big) {
                                                notused.add(new ResultLink(internals[id1], extrenals[id0], 2));
                                            } else {
                                                notused.add(new ResultLink(internals[id0], extrenals[id1], 2));
                                            }
                                            links.remove(ll);

                                            continue;
                                        }
                                        if (onetimefinished && addstepbystep) {
                                            if (ll.big) {
                                                addStepBystep.add(new ResultLink(internals[id1], extrenals[id0], 2));
                                            } else {
                                                addStepBystep.add(new ResultLink(internals[id0], extrenals[id1], 2));
                                            }
                                            links.remove(ll);
                                            continue;
                                        }
                                        if (!onetimefinished) {
                                            interactionisselected[id0][id1] = true;
                                            interactionSelected++;
                                        }
                                        if (ll.big) {
                                            if (rni[id1] == null) {
                                                if (id1 == n0rni.id) {
                                                    n0rni.temp = false;
                                                    rni[id1] = n0rni;
                                                } else {
                                                    rni[id1] = new ResultNode(id1, 1);
                                                }
                                                if (onetimefinished) {
                                                    newcreatedobjects.add(rni[id1]);
                                                }
                                            }
                                            if (rne[id0] == null) {
                                                if (id0 == n0rne.id) {
                                                    n0rne.temp = false;
                                                    rne[id0] = n0rne;
                                                } else {
                                                    rne[id0] = new ResultNode(id0, 2);
                                                }
                                                if (onetimefinished) {
                                                    newcreatedobjects.add(rne[id0]);
                                                }
                                            }
                                            rni[id1].addConnection(rne[id0]);
                                            rne[id0].addConnection(rni[id1]);
                                            if (onetimefinished) {
                                                rni[id1].addTempConnection(rne[id0]);
                                                rne[id0].addTempConnection(rni[id1]);
                                                internals[id1].addtotemp = true;
                                                extrenals[id0].addtotemp = true;
                                            }
                                            internals[id1].addsum(id0);
                                            extrenals[id0].addsum(id1);

                                            if (!rni[id1].choosed) {
                                                if (internals[id1].checking2()) {
                                                    if (onetimefinished) {
                                                        rni[id1].addtotemp = true;
                                                    }
                                                    rni[id1].changeChoosed(true);
                                                    if (!Allchoosed[id1]) {
                                                        Allchoosed[id1] = true;
                                                        if (onetimefinished) {
                                                            subtractChoosednumber++;
                                                        }
                                                        countChoosednumber++;
                                                    }
                                                }
                                            }

                                            if (!rne[id0].choosed) {
                                                if (extrenals[id0].checking2()) {
                                                    if (onetimefinished) {
                                                        rne[id0].addtotemp = true;
                                                    }
                                                    rne[id0].changeChoosed(true);
                                                    if (!Allchoosed[id0]) {
                                                        Allchoosed[id0] = true;
                                                        if (onetimefinished) {
                                                            subtractChoosednumber++;
                                                        }
                                                        countChoosednumber++;
                                                    }
                                                }
                                            }
                                        } else {
                                            if (rni[id0] == null) {
                                                if (id0 == n0rni.id) {
                                                    n0rni.temp = false;
                                                    rni[id0] = n0rni;
                                                } else {
                                                    rni[id0] = new ResultNode(id0, 1);
                                                }
                                                if (onetimefinished) {
                                                    newcreatedobjects.add(rni[id0]);
                                                }
                                            }
                                            if (rne[id1] == null) {
                                                if (id1 == n0rne.id) {
                                                    n0rne.temp = false;
                                                    rne[id1] = n0rne;
                                                } else {
                                                    rne[id1] = new ResultNode(id1, 2);
                                                }
                                                if (onetimefinished) {
                                                    newcreatedobjects.add(rne[id1]);
                                                }
                                            }
                                            rni[id0].addConnection(rne[id1]);
                                            rne[id1].addConnection(rni[id0]);
                                            if (onetimefinished) {
                                                rni[id0].addTempConnection(rne[id1]);
                                                rne[id1].addTempConnection(rni[id0]);
                                                internals[id0].addtotemp = true;
                                                extrenals[id1].addtotemp = true;
                                            }
                                            internals[id0].addsum(id1);
                                            extrenals[id1].addsum(id0);
                                            if (!rni[id0].choosed) {
                                                if (internals[id0].checking2()) {
                                                    if (onetimefinished) {
                                                        rni[id0].addtotemp = true;
                                                    }
                                                    rni[id0].changeChoosed(true);
                                                    if (!Allchoosed[id0]) {
                                                        Allchoosed[id0] = true;
                                                        if (onetimefinished) {
                                                            subtractChoosednumber++;
                                                        }
                                                        countChoosednumber++;
                                                    }
                                                }
                                            }
                                            if (!rne[id1].choosed) {
                                                if (extrenals[id1].checking2()) {
                                                    if (onetimefinished) {
                                                        rne[id1].addtotemp = true;
                                                    }
                                                    rne[id1].changeChoosed(true);
                                                    if (!Allchoosed[id1]) {
                                                        Allchoosed[id1] = true;
                                                        if (onetimefinished) {
                                                            subtractChoosednumber++;
                                                        }
                                                        countChoosednumber++;
                                                    }
                                                }
                                            }
                                        }


//
                                    } else if (end) {
                                        int id0 = ll.end.id;
                                        if (!onetimefinished) {
                                            boolean notexisted = false;
                                            for (int i = 0; i < internals[id0].interactions2[0].length; i++) {
                                                int temp = internals[id0].interactions2[0][i];
                                                if (!interactionisselected[id0][i] && !interactionisselected[i][id0]) {
                                                    notexisted = true;
                                                    break;
                                                }
                                            }
                                            if (!notexisted) {
                                                notused.add(new ResultLink(internals[id0], internals[id0], 1));
                                                links.remove(ll);
                                                continue;
                                            }
                                        }
                                        if (onetimefinished && addstepbystep) {

                                            addStepBystep.add(new ResultLink(internals[id0], internals[id0], 1));
                                            links.remove(ll);
                                            continue;

                                        }
                                        if (rni[id0] == null) {
                                            if (id0 == n0rni.id) {
                                                n0rni.temp = false;
                                                rni[id0] = n0rni;

                                            } else {
                                                rni[id0] = new ResultNode(id0, 1);
                                            }
                                            if (onetimefinished) {
                                                newcreatedobjects.add(rni[id0]);
                                            }
                                            rni[id0].star = true;
                                        }
                                        for (int i = 0; i < internals[id0].interactions2[0].length; i++) {
                                            int temp = internals[id0].interactions2[0][i];
                                            if (rni[temp] != null && rni[temp].star != false) {
                                                rni[id0].addConnection(rni[temp]);
                                                rni[temp].addConnection(rni[id0]);
                                                if (onetimefinished) {
                                                    rni[temp].addTempConnection(rni[id0]);
                                                    rni[id0].addTempConnection(rni[temp]);
                                                    internals[id0].addtotemp = true;
                                                    internals[temp].addtotemp = true;
                                                }
                                                internals[id0].addsum(temp);
                                                internals[temp].addsum(id0);
                                                if (!onetimefinished && !interactionisselected[id0][temp] && !interactionisselected[temp][id0]) {
                                                    interactionisselected[id0][temp] = true;
                                                    interactionSelected++;
                                                }
                                                if (!rni[temp].choosed) {
                                                    if (internals[temp].checking2()) {
                                                        if (onetimefinished) {
                                                            rni[temp].addtotemp = true;
                                                        }
                                                        rni[temp].changeChoosed(true);
                                                        if (!Allchoosed[temp]) {
                                                            Allchoosed[temp] = true;
                                                            if (onetimefinished) {
                                                                subtractChoosednumber++;
                                                            }
                                                            countChoosednumber++;
                                                        }
                                                    }
                                                }

                                            }
                                        }
                                        if (!rni[id0].choosed) {
                                            if (internals[id0].checking2()) {
                                                if (onetimefinished) {
                                                    rni[id0].addtotemp = true;
                                                }
                                                rni[id0].changeChoosed(true);
                                                if (!Allchoosed[id0]) {
                                                    Allchoosed[id0] = true;
                                                    if (onetimefinished) {
                                                        subtractChoosednumber++;
                                                    }
                                                    countChoosednumber++;
                                                }
                                            }
                                        }

                                    } else if (start) {
                                        int id1 = ll.start.id;
                                        if (!onetimefinished) {
                                            boolean notexisted = false;
                                            for (int i = 0; i < extrenals[id1].interactions2[0].length; i++) {
                                                int temp = extrenals[id1].interactions2[0][i];
                                                if (!interactionisselected[id1][temp] && !interactionisselected[temp][id1]) {
                                                    notexisted = true;
                                                    break;
                                                }
                                            }
                                            if (!notexisted) {
                                                notused.add(new ResultLink(extrenals[id1], extrenals[id1], 3));
                                                links.remove(ll);
                                                continue;
                                            }
                                        }
                                        if (onetimefinished && addstepbystep) {
                                            addStepBystep.add(new ResultLink(extrenals[id1], extrenals[id1], 3));
                                            links.remove(ll);
                                            continue;
                                        }
                                        if (rne[id1] == null) {
                                            if (id1 == n0rne.id) {
                                                n0rne.temp = false;
                                                rne[id1] = n0rne;
                                            } else {
                                                rne[id1] = new ResultNode(id1, 2);
                                            }
                                            rne[id1].star = true;
                                            if (onetimefinished) {
                                                newcreatedobjects.add(rne[id1]);
                                            }
                                        }
                                        for (int i = 0; i < extrenals[id1].interactions2[0].length; i++) {
                                            int temp = extrenals[id1].interactions2[0][i];
                                            if (rne[temp] != null && rne[temp].star != false) {
                                                rne[id1].addConnection(rne[temp]);
                                                rne[temp].addConnection(rne[id1]);
                                                if (onetimefinished) {
                                                    rne[id1].addConnection(rne[temp]);
                                                    rne[temp].addConnection(rne[id1]);
                                                    extrenals[id1].addtotemp = true;
                                                    extrenals[temp].addtotemp = true;
                                                }
                                                extrenals[id1].addsum(temp);
                                                extrenals[temp].addsum(id1);
                                                if (!onetimefinished && !interactionisselected[id1][temp] && !interactionisselected[temp][id1]) {
                                                    interactionisselected[id1][temp] = true;
                                                    interactionSelected++;
                                                }
                                                if (!rne[temp].choosed) {
                                                    if (extrenals[temp].checking2()) {
                                                        if (onetimefinished) {
                                                            rne[temp].addtotemp = true;
                                                        }
                                                        rne[temp].changeChoosed(true);
                                                        if (!Allchoosed[temp]) {
                                                            Allchoosed[temp] = true;
                                                            if (onetimefinished) {
                                                                subtractChoosednumber++;
                                                            }
                                                            countChoosednumber++;
                                                        }
                                                    }
                                                }

                                            }
                                        }
                                        if (!rne[id1].choosed) {
                                            if (extrenals[id1].checking2()) {
                                                if (onetimefinished) {
                                                    rne[id1].addtotemp = true;
                                                }
                                                rne[id1].changeChoosed(true);
                                                if (!Allchoosed[id1]) {
                                                    Allchoosed[id1] = true;
                                                    if (onetimefinished) {
                                                        subtractChoosednumber++;
                                                    }
                                                    countChoosednumber++;
                                                }
                                            }
                                        }

                                    }
                                    links.remove(ll);
                                    lastlink = ll;
                                    // ll.start.forwardLinks.
                                    if (onetimefinished) {
                                        addstepbystep = true;
                                    }

                                }
                                if (ll.value < 0) {
                                    System.out.println("error 1");
                                }
                            }
                            if (wehavestart) {
                                s.forwardLinks.get(ns)[0].value -= valu;
                                if (onetimefinished) {
                                    valueOfsourcesubtracted += valu;
                                }
                            }
                            bt.forwardLinks.get(t)[0].value -= valu;
                            if (onetimefinished) {
                                valueOfdestinationsubtracted += valu;
                            }
                            if (s.forwardLinks.get(ns)[0].value == 0) {
                                System.out.println("all vms must be internal--> " + internalSum);
                                onechoosed = true;
                                answerfound = true;
                                onetimefinished = false;
                                break;
                            } else if (bt.forwardLinks.get(t)[0].value == 0) {
                                System.out.println("all vms must be external--> " + externalSum);
                                onechoosed = true;
                                answerfound = true;
                                onetimefinished = false;

                                break;
                            }
                            System.out.println(Arrays.toString(rni));
                            System.out.println(Arrays.toString(rne));
                            for (int i = 0; i < icosts.length; i++) {
                                System.out.println("-------------------");
                                for (int j = 0; j < icosts[i].length; j++) {
                                    System.out.print(icosts[i][j] + " ");
                                }
                                System.out.println();
                            }

                        }
                        //******
                        else {
                            System.out.println("called 3");
                            onechoosed = true;
                            Node starting = null;
                            int valll = links1[select].value = links1[select].value;
                            Set<Node> endNodes = new HashSet<Node>();

                            for (Node nn : links1[select].start.fathers) {
                                if (ns.forwardLinks.get(nn)[0].value >= links1[select].value) {
                                    starting = nn;
                                    wehavestart = true;
                                    break;
                                }
                            }
                            //find pathes
                            Set<Link> pathes = new HashSet<Link>();
                            if (wehavestart) {
                                System.out.println("we have start");
                                pathes.add(ns.forwardLinks.get(starting)[0]);
                                for (Node n : starting.forwardLinks.keySet()) {
                                    Collection<Link[]> midlinks = n.forwardLinks.values();
                                    for (Link[] ls : midlinks) {
                                        for (int c = 0; c < ls.length; c++) {
                                            if (ls[c].value != 0) {
                                                pathes.add(ls[c]);
                                                System.out.println("path which added-->" + ls[c]);
                                                endNodes.addAll(ls[c].end.forwardNodes.keySet());
                                            }
                                            if (ls[c].value != 0 && ls[c].value < links1[select].value) {
                                                System.out.println("cant subtract 3");
                                            }
                                        }
                                    }
                                }
                                for (Node nn : endNodes) {
                                    if (nn.forwardLinks.get(bt)[0].value >= links1[select].value) {
                                        pathes.add(nn.forwardLinks.get(bt)[0]);
                                        wehaveend = true;
                                        break;
                                    }
                                }
                            } else {
                                System.out.println("we dont have start");
                                Collection<Link[]> midlinks = links1[select].start.forwardLinks.values();
                                for (Link[] ls : midlinks) {
                                    for (int c = 0; c < ls.length; c++) {
                                        if (ls[c].value != 0) {
                                            pathes.add(ls[c]);
                                            endNodes.addAll(ls[c].end.forwardNodes.keySet());
                                        }
                                        if (ls[c].value != 0 && ls[c].value < links1[select].value) {
                                            System.out.println("cant subtract 3");
                                        }
                                    }
                                }
                                for (Node nn : endNodes) {
                                    if (nn.forwardLinks.get(bt)[0].value >= links1[select].value) {
                                        pathes.add(nn.forwardLinks.get(bt)[0]);
                                        wehaveend = true;
                                        break;
                                    }
                                }
                            }
                            int val = links1[select].value;
                            System.out.println("path " + pathes);
                            addstepbystep = false;
                            for (Link ll : pathes) {
                                boolean start = false;
                                boolean end = false;
                                ll.value -= valll;
                                if (ll.value == 0) {
                                    if (ll.start != ns) {
                                        start = true;
                                        // selected.add(ll.start.id);
                                    }
                                    if (ll.end != bt) {
                                        end = true;
                                        // selected.add(ll.end.id);
                                    }
                                    if (start && end) {
                                        int id0 = ll.start.fathers.get(0).id;
                                        int id1 = ll.start.fathers.get(1).id;
                                        if (!onetimefinished && (interactionisselected[id0][id1] || interactionisselected[id1][id0])) {
                                            if (ll.big) {
                                                notused.add(new ResultLink(internals[id1], extrenals[id0], 2));
                                            } else {
                                                notused.add(new ResultLink(internals[id0], extrenals[id1], 2));
                                            }
                                            links.remove(ll);

                                            continue;
                                        }
                                        if (onetimefinished && addstepbystep) {
                                            if (ll.big) {
                                                addStepBystep.add(new ResultLink(internals[id1], extrenals[id0], 2));
                                            } else {
                                                addStepBystep.add(new ResultLink(internals[id0], extrenals[id1], 2));
                                            }
                                            links.remove(ll);
                                            continue;
                                        }
                                        if (!onetimefinished) {
                                            interactionisselected[id0][id1] = true;
                                            interactionSelected++;
                                        }
                                        if (ll.big) {
                                            if (rni[id1] == null) {
                                                if (id1 == n0rni.id) {
                                                    n0rni.temp = false;
                                                    rni[id1] = n0rni;
                                                } else {
                                                    rni[id1] = new ResultNode(id1, 1);
                                                }
                                                if (onetimefinished) {
                                                    newcreatedobjects.add(rni[id1]);
                                                }
                                            }
                                            if (rne[id0] == null) {
                                                if (id0 == n0rne.id) {
                                                    n0rne.temp = false;
                                                    rne[id0] = n0rne;
                                                } else {
                                                    rne[id0] = new ResultNode(id0, 2);
                                                }
                                                if (onetimefinished) {
                                                    newcreatedobjects.add(rne[id0]);
                                                }
                                            }
                                            rni[id1].addConnection(rne[id0]);
                                            rne[id0].addConnection(rni[id1]);
                                            if (onetimefinished) {
                                                rni[id1].addTempConnection(rne[id0]);
                                                rne[id0].addTempConnection(rni[id1]);
                                                internals[id1].addtotemp = true;
                                                extrenals[id0].addtotemp = true;
                                            }
                                            internals[id1].addsum(id0);
                                            extrenals[id0].addsum(id1);

                                            if (!rni[id1].choosed) {
                                                if (internals[id1].checking2()) {
                                                    if (onetimefinished) {
                                                        rni[id1].addtotemp = true;
                                                    }
                                                    rni[id1].changeChoosed(true);
                                                    if (!Allchoosed[id1]) {
                                                        Allchoosed[id1] = true;
                                                        if (onetimefinished) {
                                                            subtractChoosednumber++;
                                                        }
                                                        countChoosednumber++;
                                                    }
                                                }
                                            }

                                            if (!rne[id0].choosed) {
                                                if (extrenals[id0].checking2()) {
                                                    if (onetimefinished) {
                                                        rne[id0].addtotemp = true;
                                                    }
                                                    rne[id0].changeChoosed(true);
                                                    if (!Allchoosed[id0]) {
                                                        Allchoosed[id0] = true;
                                                        if (onetimefinished) {
                                                            subtractChoosednumber++;
                                                        }
                                                        countChoosednumber++;
                                                    }
                                                }
                                            }
                                        } else {
                                            if (rni[id0] == null) {
                                                if (id0 == n0rni.id) {
                                                    n0rni.temp = false;
                                                    rni[id0] = n0rni;
                                                } else {
                                                    rni[id0] = new ResultNode(id0, 1);
                                                }
                                                if (onetimefinished) {
                                                    newcreatedobjects.add(rni[id0]);
                                                }
                                            }
                                            if (rne[id1] == null) {
                                                if (id1 == n0rne.id) {
                                                    n0rne.temp = false;
                                                    rne[id1] = n0rne;
                                                } else {
                                                    rne[id1] = new ResultNode(id1, 2);
                                                }
                                                if (onetimefinished) {
                                                    newcreatedobjects.add(rne[id1]);
                                                }
                                            }
                                            rni[id0].addConnection(rne[id1]);
                                            rne[id1].addConnection(rni[id0]);
                                            if (onetimefinished) {
                                                rni[id0].addTempConnection(rne[id1]);
                                                rne[id1].addTempConnection(rni[id0]);
                                                internals[id0].addtotemp = true;
                                                extrenals[id1].addtotemp = true;
                                            }
                                            internals[id0].addsum(id1);
                                            extrenals[id1].addsum(id0);
                                            if (!rni[id0].choosed) {
                                                if (internals[id0].checking2()) {
                                                    if (onetimefinished) {
                                                        rni[id0].addtotemp = true;
                                                    }
                                                    rni[id0].changeChoosed(true);
                                                    if (!Allchoosed[id0]) {
                                                        Allchoosed[id0] = true;
                                                        if (onetimefinished) {
                                                            subtractChoosednumber++;
                                                        }
                                                        countChoosednumber++;
                                                    }
                                                }
                                            }
                                            if (!rne[id1].choosed) {
                                                if (extrenals[id1].checking2()) {
                                                    if (onetimefinished) {
                                                        rne[id1].addtotemp = true;
                                                    }
                                                    rne[id1].changeChoosed(true);
                                                    if (!Allchoosed[id1]) {
                                                        Allchoosed[id1] = true;
                                                        if (onetimefinished) {
                                                            subtractChoosednumber++;
                                                        }
                                                        countChoosednumber++;
                                                    }
                                                }
                                            }
                                        }


//
                                    } else if (end) {
                                        int id0 = ll.end.id;
                                        if (!onetimefinished) {
                                            boolean notexisted = false;
                                            for (int i = 0; i < internals[id0].interactions2[0].length; i++) {
                                                int temp = internals[id0].interactions2[0][i];
                                                if (!interactionisselected[id0][temp] && !interactionisselected[temp][id0]) {
                                                    notexisted = true;
                                                    break;
                                                }
                                            }
                                            if (!notexisted) {
                                                notused.add(new ResultLink(internals[id0], internals[id0], 1));
                                                links.remove(ll);
                                                continue;
                                            }
                                        }
                                        if (onetimefinished && addstepbystep) {

                                            addStepBystep.add(new ResultLink(internals[id0], internals[id0], 1));
                                            links.remove(ll);
                                            continue;

                                        }
                                        if (rni[id0] == null) {
                                            if (id0 == n0rni.id) {
                                                n0rni.temp = false;
                                                rni[id0] = n0rni;

                                            } else {
                                                rni[id0] = new ResultNode(id0, 1);
                                            }
                                            if (onetimefinished) {
                                                newcreatedobjects.add(rni[id0]);
                                            }
                                            rni[id0].star = true;
                                        }
                                        for (int i = 0; i < internals[id0].interactions2[0].length; i++) {
                                            int temp = internals[id0].interactions2[0][i];
                                            if (rni[temp] != null && rni[temp].star != false) {
                                                rni[id0].addConnection(rni[temp]);
                                                rni[temp].addConnection(rni[id0]);
                                                if (onetimefinished) {
                                                    rni[temp].addTempConnection(rni[id0]);
                                                    rni[id0].addTempConnection(rni[temp]);
                                                    internals[id0].addtotemp = true;
                                                    internals[temp].addtotemp = true;
                                                }
                                                internals[id0].addsum(temp);
                                                internals[temp].addsum(id0);
                                                if (!onetimefinished && !interactionisselected[id0][temp] && !interactionisselected[temp][id0]) {
                                                    interactionisselected[id0][temp] = true;
                                                    interactionSelected++;
                                                }
                                                if (!rni[temp].choosed) {
                                                    if (internals[temp].checking2()) {
                                                        if (onetimefinished) {
                                                            rni[temp].addtotemp = true;
                                                        }
                                                        rni[temp].changeChoosed(true);
                                                        if (!Allchoosed[temp]) {
                                                            Allchoosed[temp] = true;
                                                            if (onetimefinished) {
                                                                subtractChoosednumber++;
                                                            }
                                                            countChoosednumber++;
                                                        }
                                                    }
                                                }

                                            }
                                        }
                                        if (!rni[id0].choosed) {
                                            if (internals[id0].checking2()) {
                                                if (onetimefinished) {
                                                    rni[id0].addtotemp = true;
                                                }
                                                rni[id0].changeChoosed(true);
                                                if (!Allchoosed[id0]) {
                                                    Allchoosed[id0] = true;
                                                    if (onetimefinished) {
                                                        subtractChoosednumber++;
                                                    }
                                                    countChoosednumber++;
                                                }
                                            }
                                        }

                                    } else if (start) {
                                        int id1 = ll.start.id;
                                        if (!onetimefinished) {
                                            boolean notexisted = false;
                                            for (int i = 0; i < extrenals[id1].interactions2[0].length; i++) {
                                                int temp = extrenals[id1].interactions2[0][i];
                                                if (!interactionisselected[id1][temp] && !interactionisselected[temp][id1]) {
                                                    notexisted = true;
                                                    break;
                                                }
                                            }
                                            if (!notexisted) {
                                                notused.add(new ResultLink(extrenals[id1], extrenals[id1], 3));
                                                links.remove(ll);
                                                continue;
                                            }
                                        }
                                        if (onetimefinished && addstepbystep) {
                                            addStepBystep.add(new ResultLink(extrenals[id1], extrenals[id1], 3));
                                            links.remove(ll);
                                            continue;
                                        }
                                        if (rne[id1] == null) {
                                            if (id1 == n0rne.id) {
                                                n0rne.temp = false;
                                                rne[id1] = n0rne;
                                            } else {
                                                rne[id1] = new ResultNode(id1, 2);
                                            }
                                            rne[id1].star = true;
                                            if (onetimefinished) {
                                                newcreatedobjects.add(rne[id1]);
                                            }
                                        }
                                        for (int i = 0; i < extrenals[id1].interactions2[0].length; i++) {
                                            int temp = extrenals[id1].interactions2[0][i];
                                            if (rne[temp] != null && rne[temp].star != false) {
                                                rne[id1].addConnection(rne[temp]);
                                                rne[temp].addConnection(rne[id1]);
                                                if (onetimefinished) {
                                                    rne[id1].addConnection(rne[temp]);
                                                    rne[temp].addConnection(rne[id1]);
                                                    extrenals[id1].addtotemp = true;
                                                    extrenals[temp].addtotemp = true;
                                                }
                                                extrenals[id1].addsum(temp);
                                                extrenals[temp].addsum(id1);
                                                if (!onetimefinished && !interactionisselected[id1][temp] && !interactionisselected[temp][id1]) {
                                                    interactionisselected[id1][temp] = true;
                                                    interactionSelected++;
                                                }
                                                if (!rne[temp].choosed) {
                                                    if (extrenals[temp].checking2()) {
                                                        if (onetimefinished) {
                                                            rne[temp].addtotemp = true;
                                                        }
                                                        rne[temp].changeChoosed(true);
                                                        if (!Allchoosed[temp]) {
                                                            Allchoosed[temp] = true;
                                                            if (onetimefinished) {
                                                                subtractChoosednumber++;
                                                            }
                                                            countChoosednumber++;
                                                        }
                                                    }
                                                }

                                            }
                                        }
                                        if (!rne[id1].choosed) {
                                            if (extrenals[id1].checking2()) {
                                                if (onetimefinished) {
                                                    rne[id1].addtotemp = true;
                                                }
                                                rne[id1].changeChoosed(true);
                                                if (!Allchoosed[id1]) {
                                                    Allchoosed[id1] = true;
                                                    if (onetimefinished) {
                                                        subtractChoosednumber++;
                                                    }
                                                    countChoosednumber++;
                                                }
                                            }
                                        }

                                    }
                                    links.remove(ll);
                                    lastlink = ll;
                                    // ll.start.forwardLinks.
                                    if (onetimefinished) {
                                        addstepbystep = true;
                                    }

                                }
                                if (ll.value < 0) {
                                    System.out.println("error 1");
                                }
                            }
                            if (wehavestart) {
                                s.forwardLinks.get(ns)[0].value -= valll;
                                if (onetimefinished) {
                                    valueOfsourcesubtracted += valll;
                                }
                            }
                            if (wehaveend) {
                                bt.forwardLinks.get(t)[0].value -= valll;
                                if (onetimefinished) {
                                    valueOfsourcesubtracted += valll;
                                }
                            }
                            if (s.forwardLinks.get(ns)[0].value == 0) {
                                System.out.println("all vms must be internal--> " + internalSum);
                                onechoosed = true;
                                answerfound = true;
                                onetimefinished = false;

                                break;
                            } else if (bt.forwardLinks.get(t)[0].value == 0) {
                                System.out.println("all vms must be external--> " + externalSum);
                                onechoosed = true;
                                answerfound = true;
                                onetimefinished = false;

                                break;
                            }
                            System.out.println(Arrays.toString(rni));
                            System.out.println(Arrays.toString(rne));
                            for (int i = 0; i < icosts.length; i++) {
                                System.out.println("-------------------");
                                for (int j = 0; j < icosts[i].length; j++) {
                                    System.out.print(icosts[i][j] + " ");
                                }
                                System.out.println();
                            }

                        }
                    }
                }
            }
            if (!answerfound) {
                onetimefinished = true;
            }
        }

        System.out.println(Arrays.toString(resultin));
        System.out.println(Arrays.toString(resultout));
        int sum=0;
        for (int i = 0; i < CostWithExternalServices.length; i++) {
            for (int j = i+1 ; j < CostWithExternalServices.length ; j++) {
                if(interactions[i][j]==1){
                    if(resultin[i] && resultout[j]){
                        sum+=icosts2[i][j];
                    }else if(resultin[j] && resultout[i]){
                        sum+=icosts2[j][i];
                    }
                }
            }
        }
        for (int i = 0; i < CostWithExternalServices.length; i++) {
            if(resultin[i]){
                sum+=CostWithExternalServices[i];
            }
        }
        for (int i = 0; i < CostWithExternalServices.length; i++) {
            if(resultout[i]){
                sum+=CostWithInternalServices[i];
            }
        }
        System.out.println("result cost computed--> "+sum);
        char[] places=new char[CostWithExternalServices.length];
        computeCostsBruteForce(places.length , places , interactions , icosts2 , CostWithExternalServices , CostWithInternalServices);
        for (int i = 0; i < icosts.length; i++) {
            System.out.println("-------------------");
            for (int j = 0; j < icosts2[i].length; j++) {
                System.out.print(icosts2[i][j] + " ");
            }
            System.out.println();
        }

        ///
    }

    private void computeCostsBruteForce(int n , char[]  places , int[][] interactions , int[][] icosts2 ,int[] CostWithExternalServices , int[] CostWithInternalServices ){
        if(n==0){
            int sum=0;
            for (int i = 0; i < places.length; i++) {
                for (int j = i+1 ; j < places.length ; j++) {
                    if(interactions[i][j]==1){
                        if(places[i]=='i' && places[j]=='o'){
                            sum+=icosts2[i][j];
                        }else if(places[j]=='i' && places[i]=='o'){
                            sum+=icosts2[j][i];
                        }
                    }
                }
            }
            for (int i = 0; i < places.length; i++) {
                if(places[i]=='i'){
                    sum+=CostWithExternalServices[i];
                }
            }
            for (int i = 0; i < places.length; i++) {
                if(places[i]=='o'){
                    sum+=CostWithInternalServices[i];
                }
            }
            System.out.println(Arrays.toString(places)+"--> "+ sum);
        }else{
            places[places.length-n]='i';
            computeCostsBruteForce(n-1 , places , interactions,icosts2,CostWithExternalServices,CostWithInternalServices);
            places[places.length-n]='o';
            computeCostsBruteForce(n-1 , places,interactions,icosts2,CostWithExternalServices,CostWithInternalServices);
        }
    }
    private void checkWeHaveSolution(Set<Integer> selected, Vector<Integer> ids, int[] CostWithExternalServices, int[][] interactions, int[] CostWithInternalServices) {

        if (selected.containsAll(ids)) {

        }
    }
}
